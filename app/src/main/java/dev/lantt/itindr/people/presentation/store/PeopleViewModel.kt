package dev.lantt.itindr.people.presentation.store

import dev.lantt.itindr.core.presentation.mvi.MviStore
import dev.lantt.itindr.people.presentation.state.PeopleMviEffect
import dev.lantt.itindr.people.presentation.state.PeopleMviIntent
import dev.lantt.itindr.people.presentation.state.PeopleMviState
import kotlinx.coroutines.CoroutineDispatcher

class PeopleViewModel(
    middleware: PeopleMiddleware,
    reducer: PeopleReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<PeopleMviState, PeopleMviIntent, PeopleMviEffect>(middleware, reducer, defaultDispatcher) {
    override fun initialStateProvider(): PeopleMviState = PeopleMviState()

    init {
        dispatch(PeopleMviIntent.LoadInitialPeople)
    }
}