package dev.lantt.itindr.people.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviIntent
import dev.lantt.itindr.profile.domain.entity.Profile

sealed interface PeopleMviIntent : MviIntent {
    data class PeoplePageRetrieved(val peoplePage: List<Profile>) : PeopleMviIntent
    data object PeopleEmptyPageRetrieved : PeopleMviIntent
    data object PeopleError : PeopleMviIntent

    data object LoadMorePeople : PeopleMviIntent
    data object PeopleEnded : PeopleMviIntent
}