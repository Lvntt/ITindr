package dev.lantt.itindr.profile.presentation.store.otherprofile

import dev.lantt.itindr.core.presentation.mvi.Reducer
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class OtherProfileReducer : Reducer<OtherProfileMviState, OtherProfileMviIntent, OtherProfileMviEffect> {

    private val effectsFlow = MutableSharedFlow<OtherProfileMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<OtherProfileMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(
        state: OtherProfileMviState,
        intent: OtherProfileMviIntent
    ): OtherProfileMviState {
        return when (intent) {
            is OtherProfileMviIntent.UserLiked -> state.copy(isLoading = true)
            is OtherProfileMviIntent.UserDisliked -> state.copy(isLoading = true)
            is OtherProfileMviIntent.MutualLike -> {
                sendEffect(OtherProfileMviEffect.Match(intent.userId))
                state.copy(isLoading = false)
            }
            is OtherProfileMviIntent.GoBack -> {
                sendEffect(OtherProfileMviEffect.GoBack(intent.userIdToRemove))
                state.copy(isLoading = false)
            }
            OtherProfileMviIntent.NetworkError -> {
                sendEffect(OtherProfileMviEffect.ShowError)
                state.copy(isLoading = false)
            }
        }
    }

    private fun sendEffect(effect: OtherProfileMviEffect) {
        effectsFlow.tryEmit(effect)
    }
}