package dev.lantt.itindr.people.presentation.store

import dev.lantt.itindr.core.presentation.mvi.Reducer
import dev.lantt.itindr.people.presentation.state.PeopleMviEffect
import dev.lantt.itindr.people.presentation.state.PeopleMviIntent
import dev.lantt.itindr.people.presentation.state.PeopleMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PeopleReducer : Reducer<PeopleMviState, PeopleMviIntent, PeopleMviEffect> {

    private val effectsFlow = MutableSharedFlow<PeopleMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<PeopleMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(state: PeopleMviState, intent: PeopleMviIntent): PeopleMviState {
        return when (intent) {
            PeopleMviIntent.LoadInitialPeople -> state.copy(isLoading = true)
            is PeopleMviIntent.ReplacePeople -> state.copy(people = intent.people, isLoading = false)
            PeopleMviIntent.LoadMorePeople -> state.copy(isLoading = true)
            is PeopleMviIntent.PeoplePageRetrieved -> state.copy(people = state.people + intent.peoplePage, isLoading = false, currentPage = state.currentPage + 1)
            PeopleMviIntent.PeopleEmptyPageRetrieved -> state.copy(currentPage = state.currentPage + 1)
            PeopleMviIntent.PeopleError -> {
                sendEffect(PeopleMviEffect.ShowError)
                state.copy(isLoading = false)
            }
            PeopleMviIntent.PeopleEnded -> state.copy(isLoading = false, isEnded = true)
            is PeopleMviIntent.RemovePersonFromList -> state
        }
    }

    private fun sendEffect(effect: PeopleMviEffect) {
        effectsFlow.tryEmit(effect)
    }
}