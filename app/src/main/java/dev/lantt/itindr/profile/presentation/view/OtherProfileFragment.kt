package dev.lantt.itindr.profile.presentation.view

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.lantt.itindr.R
import dev.lantt.itindr.core.constants.Constants.ARG_PROFILE_ID
import dev.lantt.itindr.core.constants.Constants.PERSON_REQUEST_KEY
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.RootRouter
import dev.lantt.itindr.core.presentation.navigation.Screens.MatchScreen
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.FragmentOtherProfileBinding
import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.feed.presentation.view.InterestsAdapter
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviState
import dev.lantt.itindr.profile.presentation.store.otherprofile.OtherProfileViewModel
import org.koin.android.ext.android.inject

private const val ARG_OTHER_PROFILE = "other_profile"

class OtherProfileFragment : MviFragment<OtherProfileMviState, OtherProfileMviIntent, OtherProfileMviEffect>() {
    private var _binding: FragmentOtherProfileBinding? = null
    private val binding get() = _binding!!

    override val store: OtherProfileViewModel by inject()
    private val router: RootRouter by inject()
    private val toastManager: ToastManager by inject()

    private var loadingDialog: AlertDialog? = null

    private var profile: UiProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_OTHER_PROFILE, UiProfile::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(ARG_OTHER_PROFILE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherProfileBinding.inflate(inflater, container, false)

        profile?.let { uiProfile ->
            if (uiProfile.avatarUrl != null) {
                binding.userAvatarImage.clipToOutline = true
                binding.userAvatarImage.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.userAvatarImage.loadImageWithShimmer(uiProfile.avatarUrl)
            }
            binding.userNameText.text = uiProfile.name
            binding.aboutUserText.text = uiProfile.aboutMyself

            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.apply {
                justifyContent = JustifyContent.CENTER
            }

            binding.interestsRecyclerView.apply {
                adapter = InterestsAdapter(uiProfile.topics)
                setLayoutManager(layoutManager)
            }

            binding.likeButton.setOnClickListener {
                store.dispatch(OtherProfileMviIntent.UserLiked(uiProfile.id))
            }
            binding.dislikeButton.setOnClickListener {
                store.dispatch(OtherProfileMviIntent.UserDisliked(uiProfile.id))
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            router.exit()
        }

        return binding.root
    }

    override fun handleEffect(effect: OtherProfileMviEffect) {
        when (effect) {
            is OtherProfileMviEffect.Match -> {
                // TODO test
                router.exit()
                router.forwardAbove(MatchScreen(effect.userId))
            }
            is OtherProfileMviEffect.GoBack -> {
                parentFragmentManager.setFragmentResult(PERSON_REQUEST_KEY, bundleOf(ARG_PROFILE_ID to effect.userIdToRemove))
                router.exit()
            }
            OtherProfileMviEffect.ShowError -> toastManager.showToast(context, R.string.networkError)
        }
    }

    override fun render(state: OtherProfileMviState) {
        if (state.isLoading) {
            showLoadingDialog()
        } else {
            dismissLoadingDialog()
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

    companion object {
        @JvmStatic
        fun newInstance(uiProfile: UiProfile) =
            OtherProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_OTHER_PROFILE, uiProfile)
                }
            }
    }
}