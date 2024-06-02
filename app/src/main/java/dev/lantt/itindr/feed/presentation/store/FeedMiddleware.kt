package dev.lantt.itindr.feed.presentation.store

import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.feed.domain.usecase.DislikeUserUseCase
import dev.lantt.itindr.feed.domain.usecase.GetFeedUseCase
import dev.lantt.itindr.feed.domain.usecase.LikeUserUseCase
import dev.lantt.itindr.feed.presentation.state.FeedMviIntent
import dev.lantt.itindr.feed.presentation.state.FeedMviState
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FeedMiddleware(
    private val profileMapper: ProfileMapper,
    private val getFeedUseCase: GetFeedUseCase,
    private val likeUserUseCase: LikeUserUseCase,
    private val dislikeUserUseCase: DislikeUserUseCase,
) : Middleware<FeedMviState, FeedMviIntent> {
    override fun resolve(state: FeedMviState, intent: FeedMviIntent): Flow<FeedMviIntent>? {
        return when (intent) {
            FeedMviIntent.FeedRequested -> flow {
                runCatching {
                    getFeedUseCase()
                }.fold(
                    onSuccess = { remoteFeed ->
                        if (remoteFeed.isNotEmpty()) {
                            val feed = remoteFeed.map { profileMapper.toUiProfile(it) }
                            emit(FeedMviIntent.FeedLoadSuccess(feed))
                        } else {
                            emit(FeedMviIntent.FeedEmpty)
                        }
                    },
                    onFailure = {
                        emit(FeedMviIntent.FeedLoadError)
                    }
                )
            }
            is FeedMviIntent.UserLiked -> flow {
                runCatching {
                    likeUserUseCase(intent.id)
                }.fold(
                    onSuccess = {
                        if (it.isMutual) {
                            emit(FeedMviIntent.MutualLike(intent.id))
                        } else {
                            emit(FeedMviIntent.NextUserRequested)
                        }
                    },
                    onFailure = {
                        emit(FeedMviIntent.NetworkError)
                    }
                )
            }
            is FeedMviIntent.UserDisliked -> flow {
                runCatching {
                    dislikeUserUseCase(intent.id)
                }.fold(
                    onSuccess = {
                        emit(FeedMviIntent.NextUserRequested)
                    },
                    onFailure = {
                        emit(FeedMviIntent.NetworkError)
                    }
                )
            }
            FeedMviIntent.NextUserRequested -> flow {
                // TODO efficiency?
                val updatedFeed = state.feed.drop(1)
                if (updatedFeed.isEmpty()) {
                    emit(FeedMviIntent.FeedEmpty)
                } else {
                    emit(FeedMviIntent.ShowNextUser(updatedFeed))
                }
            }
            else -> null
        }
    }
}