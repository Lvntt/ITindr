package dev.lantt.itindr.match.presentation.store

import dev.lantt.itindr.core.presentation.mvi.MviStore
import dev.lantt.itindr.match.presentation.state.MatchMviEffect
import dev.lantt.itindr.match.presentation.state.MatchMviIntent
import dev.lantt.itindr.match.presentation.state.MatchMviState
import kotlinx.coroutines.CoroutineDispatcher

class MatchViewModel(
    middleware: MatchMiddleware,
    reducer: MatchReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<MatchMviState, MatchMviIntent, MatchMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): MatchMviState = MatchMviState()
}