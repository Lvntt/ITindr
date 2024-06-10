package dev.lantt.itindr.profile.presentation.store.profile

import dev.lantt.itindr.core.presentation.mvi.MviStore
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviState
import kotlinx.coroutines.CoroutineDispatcher

class ProfileViewModel(
    middleware: ProfileMiddleware,
    reducer: ProfileReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<ProfileMviState, ProfileMviIntent, ProfileMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): ProfileMviState = ProfileMviState()

    init {
        dispatch(ProfileMviIntent.LoadProfile)
    }
}