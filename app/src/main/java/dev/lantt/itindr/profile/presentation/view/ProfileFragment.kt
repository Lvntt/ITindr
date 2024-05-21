package dev.lantt.itindr.profile.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import dev.lantt.itindr.R
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.databinding.FragmentProfileBinding
import dev.lantt.itindr.profile.presentation.state.ProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.ProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.ProfileMviState
import dev.lantt.itindr.profile.presentation.store.ProfileViewModel
import dev.lantt.itindr.core.presentation.utils.ToastManager
import org.koin.android.ext.android.inject

class ProfileFragment : MviFragment<ProfileMviState, ProfileMviIntent, ProfileMviEffect>() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override val store: ProfileViewModel by inject()
    private val toastManager: ToastManager by inject()

    private var loadingDialog: AlertDialog? = null
    private val pickAvatarLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            store.dispatch(ProfileMviIntent.AvatarPicked(it))
        }
    }
    private var topicsListAdapter: TopicsListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val topicsRecyclerView = binding.topicsRecyclerView
        topicsListAdapter = TopicsListAdapter(
            onTopicClick = {
                store.dispatch(ProfileMviIntent.TopicChosen(it))
            }
        )
        topicsListAdapter?.submitList(store.state.value.topics)

        topicsRecyclerView.adapter = topicsListAdapter

        binding.nameTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(ProfileMviIntent.NameChanged(text.toString()))
        }

        binding.saveButton.setOnClickListener {
            store.dispatch(ProfileMviIntent.SaveRequested)
        }

        return binding.root
    }

    override fun handleEffect(effect: ProfileMviEffect) {
        when (effect) {
            ProfileMviEffect.HandleSuccess -> TODO()
            ProfileMviEffect.ShowTopicsError -> toastManager.showToast(context, R.string.topicsError)
            ProfileMviEffect.ShowSaveError -> toastManager.showToast(context, R.string.saveError)
        }
    }

    override fun render(state: ProfileMviState) {
        topicsListAdapter?.submitList(store.state.value.topics)

        binding.loadingProgressBar.isVisible = state.areTopicsLoading

        if (state.avatarUri != null) {
            binding.profileImageView.clipToOutline = true
            binding.profileImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.profileImageView.setImageURI(state.avatarUri)
            binding.choosePhotoTextView.text = getString(R.string.removePhoto)
            binding.choosePhotoTextView.setOnClickListener {
                store.dispatch(ProfileMviIntent.AvatarRemoved)
            }
        } else {
            binding.profileImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_user))
            binding.choosePhotoTextView.text = getString(R.string.choosePhoto)
            binding.choosePhotoTextView.setOnClickListener {
                pickAvatarLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType("image/jpeg")))
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