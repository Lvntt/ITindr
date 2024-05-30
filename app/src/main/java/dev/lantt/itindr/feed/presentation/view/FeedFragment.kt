package dev.lantt.itindr.feed.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import com.facebook.shimmer.Shimmer
import com.github.terrakok.cicerone.Router
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.lantt.itindr.R
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.Screens.AboutUser
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.FragmentFeedBinding
import dev.lantt.itindr.feed.presentation.state.FeedMviEffect
import dev.lantt.itindr.feed.presentation.state.FeedMviIntent
import dev.lantt.itindr.feed.presentation.state.FeedMviState
import dev.lantt.itindr.feed.presentation.store.FeedViewModel
import org.koin.android.ext.android.inject

class FeedFragment : MviFragment<FeedMviState, FeedMviIntent, FeedMviEffect>() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override val store: FeedViewModel by inject()
    private val toastManager: ToastManager by inject()
    private val router: Router by inject()

    private lateinit var shimmer: Shimmer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)

        shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1000L)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        return binding.root
    }

    override fun handleEffect(effect: FeedMviEffect) {
        when (effect) {
            FeedMviEffect.Match -> TODO()
            FeedMviEffect.ShowError -> toastManager.showToast(context, R.string.networkError)
            is FeedMviEffect.GoToAboutUser -> router.navigateTo(AboutUser(effect.profile))
        }
    }

    override fun render(state: FeedMviState) {
        if (state.isLoading) {
            binding.shimmerLayout.apply {
                isVisible = true
                setShimmer(shimmer)
                startShimmer()
            }
        } else {
            binding.shimmerLayout.apply {
                isVisible = false
                stopShimmer()
            }
        }

        val shouldShowContent = !state.isEmpty && !state.isLoading

        binding.placeholderImage.isVisible = state.isEmpty
        binding.placeholderText.isVisible = state.isEmpty

        binding.userAvatarImage.isVisible = shouldShowContent
        binding.userNameText.isVisible = shouldShowContent
        binding.interestsRecyclerView.isVisible = shouldShowContent
        binding.aboutUserText.isVisible = shouldShowContent
        binding.dislikeButton.isVisible = shouldShowContent
        binding.likeButton.isVisible = shouldShowContent

        // TODO different shimmer (image) + color
        // TODO about myself not working on register?
        state.currentProfile.avatarUrl?.let {
            binding.userAvatarImage.clipToOutline = true
            binding.userAvatarImage.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.userAvatarImage.loadImageWithShimmer(state.currentProfile.avatarUrl)
        }

        binding.userNameText.text = state.currentProfile.name

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.apply {
            justifyContent = JustifyContent.CENTER
        }

        binding.interestsRecyclerView.apply {
            adapter = InterestsAdapter(state.currentProfile.topics)
            setLayoutManager(layoutManager)
        }

        binding.aboutUserText.text = state.currentProfile.aboutMyself

        binding.userAvatarImage.setOnClickListener {
            store.dispatch(FeedMviIntent.UserAvatarClicked(state.currentProfile))
        }
        binding.dislikeButton.setOnClickListener {
            store.dispatch(FeedMviIntent.UserDisliked(state.currentProfile.id))
        }
        binding.likeButton.setOnClickListener {
            store.dispatch(FeedMviIntent.UserLiked(state.currentProfile.id))
        }
    }
}