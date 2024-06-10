package dev.lantt.itindr.profile.presentation.view

import android.app.AlertDialog
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.R
import dev.lantt.itindr.core.constants.Constants.PROFILE_CHANGED
import dev.lantt.itindr.core.constants.Constants.PROFILE_CHANGED_REQUEST_KEY
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.FragmentEditProfileBinding
import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.profile.presentation.model.Topic
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviState
import dev.lantt.itindr.profile.presentation.store.edit.EditProfileViewModel
import org.koin.android.ext.android.inject
import java.io.File

private const val ARG_UI_PROFILE = "arg_ui_profile"

class EditProfileFragment : MviFragment<EditProfileMviState, EditProfileMviIntent, EditProfileMviEffect>() {

    private var profile: UiProfile? = null

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override val store: EditProfileViewModel by inject()
    private val toastManager: ToastManager by inject()
    private val router: Router by inject()

    private var takenPictureUri: Uri? = null

    private var loadingDialog: AlertDialog? = null
    private val pickAvatarLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            store.dispatch(EditProfileMviIntent.AvatarPicked(it))
        }
    }
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            takenPictureUri?.let {
                store.dispatch(EditProfileMviIntent.AvatarPicked(it))
            }
        } else {
            takenPictureUri = null
            toastManager.showToast(context, R.string.savePhotoError)
        }
    }

    private var topicsListAdapter: TopicsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_UI_PROFILE, UiProfile::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(ARG_UI_PROFILE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        profile?.let {
            store.dispatch(EditProfileMviIntent.InitProfile(it))
        }

        val topicsRecyclerView = binding.topicsRecyclerView
        topicsListAdapter = TopicsListAdapter(
            onTopicClick = {
                store.dispatch(EditProfileMviIntent.TopicChosen(it))
            }
        )
        topicsListAdapter?.submitList(
            store.state.value.newTopics.map {
                Topic(
                    id = it.id,
                    title = it.title,
                    isSelected = it.isSelected
                )
            }
            // TODO remove mapping
        )

        topicsRecyclerView.adapter = topicsListAdapter

        binding.nameTextInput.editText?.setText(store.state.value.newName, TextView.BufferType.EDITABLE)
        binding.aboutMyselfTextInput.editText?.setText(store.state.value.newAboutMyself, TextView.BufferType.EDITABLE)

        binding.nameTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(EditProfileMviIntent.NameChanged(text.toString()))
        }
        binding.aboutMyselfTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(EditProfileMviIntent.AboutMyselfChanged(text.toString()))
        }

        binding.saveButton.setOnClickListener {
            store.dispatch(EditProfileMviIntent.SaveRequested)
        }

        binding.toolbar.setNavigationOnClickListener {
            router.exit()
        }

        return binding.root
    }

    override fun handleEffect(effect: EditProfileMviEffect) {
        when (effect) {
            EditProfileMviEffect.HandleSuccess -> {
                parentFragmentManager.setFragmentResult(PROFILE_CHANGED_REQUEST_KEY, bundleOf(PROFILE_CHANGED to true))
                router.exit()
            }
            EditProfileMviEffect.ShowAvatarChoice -> showAvatarChoiceDialog(
                onMakePhoto = {
                    val tempFile = File.createTempFile("temp", ".jpg", context?.applicationContext?.filesDir).apply {
                        createNewFile()
                        deleteOnExit()
                    }
                    val tempFileUri = FileProvider.getUriForFile(requireContext(), context?.applicationContext?.packageName + ".provider", tempFile)
                    takenPictureUri = tempFileUri
                    takePictureLauncher.launch(takenPictureUri)
                },
                onChooseFromGallery = {
                    pickAvatarLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType("image/jpeg")))
                }
            )
            EditProfileMviEffect.ShowSaveError -> toastManager.showToast(context, R.string.saveError)
            EditProfileMviEffect.ShowTopicsError -> toastManager.showToast(context, R.string.topicsError)
        }
    }

    override fun render(state: EditProfileMviState) {
        topicsListAdapter?.submitList(
            store.state.value.newTopics.map {
                Topic(
                    id = it.id,
                    title = it.title,
                    isSelected = it.isSelected
                )
            }
            // TODO remove mapping
        )

        binding.loadingProgressBar.isVisible = state.areTopicsLoading

        if (state.newAvatarUri != null) {
            binding.userAvatarImage.clipToOutline = true
            binding.userAvatarImage.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.userAvatarImage.setImageURI(state.newAvatarUri)
            binding.choosePhotoTextView.text = getString(R.string.removePhoto)
            binding.choosePhotoTextView.setOnClickListener {
                store.dispatch(EditProfileMviIntent.AvatarRemoved)
            }
        } else if (state.unchangedProfile.avatarUrl != null && !state.wasAvatarChanged) {
            binding.userAvatarImage.clipToOutline = true
            binding.userAvatarImage.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.userAvatarImage.loadImageWithShimmer(state.unchangedProfile.avatarUrl)
            binding.choosePhotoTextView.text = getString(R.string.removePhoto)
            binding.choosePhotoTextView.setOnClickListener {
                store.dispatch(EditProfileMviIntent.AvatarRemoved)
            }
        } else {
            binding.userAvatarImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_user))
            binding.choosePhotoTextView.text = getString(R.string.choosePhoto)
            binding.choosePhotoTextView.setOnClickListener {
                store.dispatch(EditProfileMviIntent.AvatarChoiceRequested)
            }
        }

        // TODO add logic
        // binding.nameError.isVisible = !state.isNameValid

        if (state.isLoading) {
            showLoadingDialog()
        } else {
            dismissLoadingDialog()
        }

        binding.saveButton.isEnabled = state.isSaveAllowed
    }

    private fun showAvatarChoiceDialog(
        onMakePhoto: () -> Unit,
        onChooseFromGallery: () -> Unit
    ) {
        val options = arrayOf(
            context?.getString(R.string.makePhoto),
            context?.getString(R.string.pickFromGallery),
        )

        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.chooseAvatar))
        builder.setItems(options) { _, item ->
            when (item) {
                0 -> onMakePhoto()
                1 -> onChooseFromGallery()
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showLoadingDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.loading))
        builder.setView(layoutInflater.inflate(R.layout.loading_progress_bar, null))
        builder.setCancelable(false)

        loadingDialog = builder.create()
        loadingDialog?.show()
    }

    private fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance(uiProfile: UiProfile) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_UI_PROFILE, uiProfile)
                }
            }
    }
}