package dev.lantt.itindr.feed.presentation.store

import dev.lantt.itindr.core.presentation.mvi.MviStore
import dev.lantt.itindr.feed.presentation.state.FeedMviEffect
import dev.lantt.itindr.feed.presentation.state.FeedMviIntent
import dev.lantt.itindr.feed.presentation.state.FeedMviState
import kotlinx.coroutines.CoroutineDispatcher

class FeedViewModel(
    middleware: FeedMiddleware,
    reducer: FeedReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<FeedMviState, FeedMviIntent, FeedMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): FeedMviState = FeedMviState()

    init {
        dispatch(FeedMviIntent.FeedRequested)
    }
}