package dev.lantt.itindr.feed.presentation.store

import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.feed.domain.usecase.DislikeUserUseCase
import dev.lantt.itindr.feed.domain.usecase.GetFeedUseCase
import dev.lantt.itindr.feed.domain.usecase.LikeUserUseCase
import dev.lantt.itindr.feed.presentation.state.FeedMviIntent
import dev.lantt.itindr.feed.presentation.state.FeedMviState
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper

class FeedMiddleware(
    private val profileMapper: ProfileMapper,
    private val getFeedUseCase: GetFeedUseCase,
    private val likeUserUseCase: LikeUserUseCase,
    private val dislikeUserUseCase: DislikeUserUseCase,
) : Middleware<FeedMviState, FeedMviIntent> {
    override suspend fun resolve(state: FeedMviState, intent: FeedMviIntent): FeedMviIntent? {
        return when (intent) {
            FeedMviIntent.FeedRequested -> {
                runCatching {
                    getFeedUseCase()
                }.fold(
                    onSuccess = { remoteFeed ->
                        if (remoteFeed.isNotEmpty()) {
                            val feed = remoteFeed.map { profileMapper.toUiProfile(it) }
                            FeedMviIntent.FeedLoadSuccess(feed)
                        } else {
                            FeedMviIntent.FeedEmpty
                        }
                    },
                    onFailure = {
                        FeedMviIntent.FeedLoadError
                    }
                )
            }
            is FeedMviIntent.UserLiked -> {
                runCatching {
                    likeUserUseCase(intent.id)
                }.fold(
                    onSuccess = {
                        if (it.isMutual) {
                            FeedMviIntent.MutualLike
                        } else {
                            FeedMviIntent.NextUserRequested
                        }
                    },
                    onFailure = {
                        FeedMviIntent.NetworkError
                    }
                )
            }
            is FeedMviIntent.UserDisliked -> {
                runCatching {
                    dislikeUserUseCase(intent.id)
                }.fold(
                    onSuccess = {
                        FeedMviIntent.NextUserRequested
                    },
                    onFailure = {
                        FeedMviIntent.NetworkError
                    }
                )
            }
            FeedMviIntent.NextUserRequested -> {
                // TODO efficiency?
                val updatedFeed = state.feed.drop(1)
                if (updatedFeed.isEmpty()) {
                    FeedMviIntent.FeedEmpty
                } else {
                    FeedMviIntent.ShowNextUser(updatedFeed)
                }
            }
            else -> null
        }
    }
}