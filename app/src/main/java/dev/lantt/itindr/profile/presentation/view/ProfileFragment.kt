package dev.lantt.itindr.profile.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.github.terrakok.cicerone.Router
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.lantt.itindr.R
import dev.lantt.itindr.core.constants.Constants.PROFILE_CHANGED_REQUEST_KEY
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.Screens.EditProfile
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.FragmentProfileBinding
import dev.lantt.itindr.feed.presentation.view.InterestsAdapter
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviState
import dev.lantt.itindr.profile.presentation.store.profile.ProfileViewModel
import org.koin.android.ext.android.inject

class ProfileFragment : MviFragment<ProfileMviState, ProfileMviIntent, ProfileMviEffect>() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override val store: ProfileViewModel by inject()
    private val router: Router by inject()
    private val toastManager: ToastManager by inject()

    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        parentFragment?.parentFragmentManager?.setFragmentResultListener(PROFILE_CHANGED_REQUEST_KEY, viewLifecycleOwner) { _, _ ->
            store.dispatch(ProfileMviIntent.LoadProfile)
        }

        return binding.root
    }

    override fun handleEffect(effect: ProfileMviEffect) {
        when (effect) {
            ProfileMviEffect.ShowError -> toastManager.showToast(context, R.string.networkError)
        }
    }

    override fun render(state: ProfileMviState) {
        if (state.isLoading) {
            showLoadingDialog()
        } else {
            dismissLoadingDialog()
        }

        if (state.profile.avatarUrl != null) {
            binding.userAvatarImage.clipToOutline = true
            binding.userAvatarImage.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.userAvatarImage.loadImageWithShimmer(state.profile.avatarUrl)
        } else {
            binding.userAvatarImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_user))
        }

        binding.userNameText.text = state.profile.name

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.apply {
            justifyContent = JustifyContent.CENTER
        }

        binding.interestsRecyclerView.apply {
            adapter = InterestsAdapter(state.profile.topics)
            setLayoutManager(layoutManager)
        }

        binding.aboutUserText.text = state.profile.aboutMyself

        binding.editButton.setOnClickListener {
            router.navigateTo(EditProfile(state.profile))
        }
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