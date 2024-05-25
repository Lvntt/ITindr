package dev.lantt.itindr.profile.presentation.store

import dev.lantt.itindr.profile.presentation.state.SetupMviEffect
import dev.lantt.itindr.profile.presentation.state.SetupMviIntent
import dev.lantt.itindr.profile.presentation.state.SetupMviState
import dev.lantt.itindr.core.presentation.mvi.MviStore
import kotlinx.coroutines.CoroutineDispatcher

class SetupViewModel(
    middleware: SetupMiddleware,
    reducer: SetupReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<SetupMviState, SetupMviIntent, SetupMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): SetupMviState = SetupMviState()

    init {
        dispatch(SetupMviIntent.TopicsRequested)
    }

}