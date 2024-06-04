package dev.lantt.itindr.profile.presentation.store.setup

import dev.lantt.itindr.core.presentation.mvi.MviStore
import dev.lantt.itindr.profile.presentation.state.setup.SetupMviEffect
import dev.lantt.itindr.profile.presentation.state.setup.SetupMviIntent
import dev.lantt.itindr.profile.presentation.state.setup.SetupMviState
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