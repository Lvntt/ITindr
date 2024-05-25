package dev.lantt.itindr.profile.presentation.view

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.R
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.Screens.RootBottomNavigation
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.databinding.FragmentSetupBinding
import dev.lantt.itindr.profile.presentation.state.SetupMviEffect
import dev.lantt.itindr.profile.presentation.state.SetupMviIntent
import dev.lantt.itindr.profile.presentation.state.SetupMviState
import dev.lantt.itindr.profile.presentation.store.SetupViewModel
import org.koin.android.ext.android.inject
import java.io.File

class SetupFragment : MviFragment<SetupMviState, SetupMviIntent, SetupMviEffect>() {

    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    override val store: SetupViewModel by inject()
    private val toastManager: ToastManager by inject()
    private val router: Router by inject()

    private var takenPictureUri: Uri? = null

    private var loadingDialog: AlertDialog? = null
    private val pickAvatarLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            store.dispatch(SetupMviIntent.AvatarPicked(it))
        }
    }
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            takenPictureUri?.let {
                store.dispatch(SetupMviIntent.AvatarPicked(it))
            }
        } else {
            takenPictureUri = null
            toastManager.showToast(context, R.string.savePhotoError)
        }
    }
    private var topicsListAdapter: TopicsListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)

        val topicsRecyclerView = binding.topicsRecyclerView
        topicsListAdapter = TopicsListAdapter(
            onTopicClick = {
                store.dispatch(SetupMviIntent.TopicChosen(it))
            }
        )
        topicsListAdapter?.submitList(store.state.value.topics)

        topicsRecyclerView.adapter = topicsListAdapter

        binding.nameTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(SetupMviIntent.NameChanged(text.toString()))
        }

        binding.saveButton.setOnClickListener {
            store.dispatch(SetupMviIntent.SaveRequested)
        }

        return binding.root
    }

    override fun handleEffect(effect: SetupMviEffect) {
        when (effect) {
            SetupMviEffect.HandleSuccess -> router.newRootScreen(RootBottomNavigation())
            SetupMviEffect.ShowAvatarChoice -> showAvatarChoiceDialog(
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
            SetupMviEffect.ShowTopicsError -> toastManager.showToast(context, R.string.topicsError)
            SetupMviEffect.ShowSaveError -> toastManager.showToast(context, R.string.saveError)
        }
    }

    override fun render(state: SetupMviState) {
        topicsListAdapter?.submitList(store.state.value.topics)

        binding.loadingProgressBar.isVisible = state.areTopicsLoading

        if (state.avatarUri != null) {
            binding.profileImageView.clipToOutline = true
            binding.profileImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.profileImageView.setImageURI(state.avatarUri)
            binding.choosePhotoTextView.text = getString(R.string.removePhoto)
            binding.choosePhotoTextView.setOnClickListener {
                store.dispatch(SetupMviIntent.AvatarRemoved)
            }
        } else {
            binding.profileImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_user))
            binding.choosePhotoTextView.text = getString(R.string.choosePhoto)
            binding.choosePhotoTextView.setOnClickListener {
                store.dispatch(SetupMviIntent.AvatarChoiceRequested)
            }
        }

        binding.nameError.isVisible = !state.isNameValid

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

}