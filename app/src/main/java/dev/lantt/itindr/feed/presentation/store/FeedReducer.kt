package dev.lantt.itindr.feed.presentation.store

import dev.lantt.itindr.core.presentation.mvi.Reducer
import dev.lantt.itindr.feed.presentation.state.FeedMviEffect
import dev.lantt.itindr.feed.presentation.state.FeedMviIntent
import dev.lantt.itindr.feed.presentation.state.FeedMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FeedReducer : Reducer<FeedMviState, FeedMviIntent, FeedMviEffect> {

    private val effectsFlow = MutableSharedFlow<FeedMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<FeedMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(state: FeedMviState, intent: FeedMviIntent): FeedMviState {
        return when (intent) {
            FeedMviIntent.FeedRequested -> state.copy(isLoading = true)
            is FeedMviIntent.FeedLoadSuccess -> {
                val user = intent.feed.first()
                with (user) {
                    state.copy(
                        feed = intent.feed,
                        id = userId,
                        name = name,
                        aboutMyself = aboutMyself ?: "",
                        avatarUrl = avatar,
                        topics = topics,
                        isLoading = false
                    )
                }
            }
            FeedMviIntent.FeedLoadError -> {
                sendEffect(FeedMviEffect.ShowError)
                state.copy(isLoading = false)
            }
            FeedMviIntent.MutualLike -> {
                sendEffect(FeedMviEffect.Match)
                state.copy(isLoading = false)
            }
            is FeedMviIntent.ShowNextUser -> {
                val user = intent.updatedFeed.first()
                with (user) {
                    state.copy(
                        feed = intent.updatedFeed,
                        id = userId,
                        name = name,
                        aboutMyself = aboutMyself ?: "",
                        avatarUrl = avatar,
                        topics = topics,
                        isLoading = false
                    )
                }
            }
            FeedMviIntent.FeedEmpty -> state.copy(
                isEmpty = true,
                isLoading = false
            )
            FeedMviIntent.NetworkError -> {
                sendEffect(FeedMviEffect.ShowError)
                state.copy(isLoading = false)
            }
            FeedMviIntent.NextUserRequested -> state.copy(isLoading = false)
            is FeedMviIntent.UserLiked -> state.copy(isLoading = true)
            is FeedMviIntent.UserDisliked -> state.copy(isLoading = true)
        }
    }

    private fun sendEffect(effect: FeedMviEffect) {
        effectsFlow.tryEmit(effect)
    }

}