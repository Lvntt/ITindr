package dev.lantt.itindr.profile.presentation.store.otherprofile

import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.feed.domain.usecase.DislikeUserUseCase
import dev.lantt.itindr.feed.domain.usecase.LikeUserUseCase
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OtherProfileMiddleware(
    private val likeUserUseCase: LikeUserUseCase,
    private val dislikeUserUseCase: DislikeUserUseCase,
) : Middleware<OtherProfileMviState, OtherProfileMviIntent> {
    override fun resolve(
        state: OtherProfileMviState,
        intent: OtherProfileMviIntent
    ): Flow<OtherProfileMviIntent>? {
        return when (intent) {
            is OtherProfileMviIntent.UserLiked -> flow {
                runCatching {
                    likeUserUseCase(intent.userId)
                }.fold(
                    onSuccess = {
                        if (it.isMutual) {
                            emit(OtherProfileMviIntent.MutualLike(intent.userId))
                        } else {
                            emit(OtherProfileMviIntent.GoBack(intent.userId))
                        }
                    },
                    onFailure = {
                        emit(OtherProfileMviIntent.NetworkError)
                    }
                )
            }
            is OtherProfileMviIntent.UserDisliked -> flow {
                runCatching {
                    dislikeUserUseCase(intent.userId)
                }.fold(
                    onSuccess = {
                        emit(OtherProfileMviIntent.GoBack(intent.userId))
                    },
                    onFailure = {
                        emit(OtherProfileMviIntent.NetworkError)
                    }
                )
            }
            else -> null
        }
    }
}