package dev.lantt.itindr.people.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviIntent
import dev.lantt.itindr.feed.presentation.state.UiProfile

sealed interface PeopleMviIntent : MviIntent {
    data class PeoplePageRetrieved(val peoplePage: List<UiProfile>) : PeopleMviIntent
    data object PeopleEmptyPageRetrieved : PeopleMviIntent
    data object PeopleError : PeopleMviIntent

    data object LoadInitialPeople : PeopleMviIntent
    data class ReplacePeople(val people: List<UiProfile>) : PeopleMviIntent
    data object LoadMorePeople : PeopleMviIntent
    data object PeopleEnded : PeopleMviIntent
    data class RemovePersonFromList(val userIdToRemove: String) : PeopleMviIntent
}