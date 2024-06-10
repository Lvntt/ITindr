package dev.lantt.itindr.profile.presentation.store.profile

import dev.lantt.itindr.core.presentation.mvi.Reducer
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ProfileReducer : Reducer<ProfileMviState, ProfileMviIntent, ProfileMviEffect> {

    private val effectsFlow = MutableSharedFlow<ProfileMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<ProfileMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(state: ProfileMviState, intent: ProfileMviIntent): ProfileMviState {
        return when (intent) {
            ProfileMviIntent.LoadProfile -> state.copy(isLoading = true)
            is ProfileMviIntent.ProfileRetrieved -> state.copy(profile = intent.uiProfile, isLoading = false)
            ProfileMviIntent.NetworkError -> {
                sendEffect(ProfileMviEffect.ShowError)
                state.copy(isLoading = false)
            }
        }
    }

    private fun sendEffect(effect: ProfileMviEffect) {
        effectsFlow.tryEmit(effect)
    }
}