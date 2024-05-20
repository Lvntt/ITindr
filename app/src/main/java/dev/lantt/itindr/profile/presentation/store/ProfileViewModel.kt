package dev.lantt.itindr.profile.presentation.store

import dev.lantt.itindr.profile.presentation.state.ProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.ProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.ProfileMviState
import dev.lantt.itindr.core.presentation.mvi.MviStore
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
        dispatch(ProfileMviIntent.TopicsRequested)
    }

}