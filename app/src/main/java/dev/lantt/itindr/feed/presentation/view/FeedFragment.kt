package dev.lantt.itindr.feed.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.lantt.itindr.R
import dev.lantt.itindr.core.presentation.mvi.MviFragment
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

    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun handleEffect(effect: FeedMviEffect) {
        when (effect) {
            FeedMviEffect.Match -> TODO()
            FeedMviEffect.ShowError -> toastManager.showToast(context, R.string.networkError)
        }
    }

    override fun render(state: FeedMviState) {
        if (state.isLoading) {
            showLoadingDialog()
        } else {
            dismissLoadingDialog()

            binding.placeholderImage.isVisible = state.isEmpty
            binding.placeholderText.isVisible = state.isEmpty

            binding.userAvatarImage.isVisible = !state.isEmpty
            binding.userNameText.isVisible = !state.isEmpty
            binding.interestsRecyclerView.isVisible = !state.isEmpty
            binding.aboutUserText.isVisible = !state.isEmpty
            binding.dislikeButton.isVisible = !state.isEmpty
            binding.likeButton.isVisible = !state.isEmpty
        }

        state.avatarUrl?.let {
            binding.userAvatarImage.clipToOutline = true
            binding.userAvatarImage.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.userAvatarImage.loadImageWithShimmer(state.avatarUrl)
        }

        binding.userNameText.text = state.name

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.apply {
            justifyContent = JustifyContent.CENTER
        }

        binding.interestsRecyclerView.apply {
            adapter = InterestsAdapter(state.topics)
            setLayoutManager(layoutManager)
        }

        binding.aboutUserText.text = state.aboutMyself

        binding.dislikeButton.setOnClickListener {
            store.dispatch(FeedMviIntent.UserDisliked(state.id))
        }
        binding.likeButton.setOnClickListener {
            store.dispatch(FeedMviIntent.UserLiked(state.id))
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