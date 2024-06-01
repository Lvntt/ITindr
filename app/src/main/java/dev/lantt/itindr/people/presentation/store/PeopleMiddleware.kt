package dev.lantt.itindr.people.presentation.store

import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.people.presentation.domain.usecase.GetUserListUseCase
import dev.lantt.itindr.people.presentation.state.PeopleMviIntent
import dev.lantt.itindr.people.presentation.state.PeopleMviState
import dev.lantt.itindr.people.presentation.state.PeopleMviState.Companion.PEOPLE_PAGE_LIMIT

class PeopleMiddleware(
    private val getUserListUseCase: GetUserListUseCase,
) : Middleware<PeopleMviState, PeopleMviIntent> {
    override suspend fun resolve(state: PeopleMviState, intent: PeopleMviIntent): PeopleMviIntent? {
        return when (intent) {
            PeopleMviIntent.LoadMorePeople -> {
                runCatching {
                    getUserListUseCase(PEOPLE_PAGE_LIMIT, state.currentPage * PEOPLE_PAGE_LIMIT)
                }.fold(
                    onSuccess = { remotePeople ->
                        if (remotePeople.isEmpty()) {
                            PeopleMviIntent.PeopleEnded
                        } else {
                            val peoplePage = remotePeople.filter { it.name.isNotBlank() }
                            if (peoplePage.isEmpty()) {
                                PeopleMviIntent.PeopleEmptyPageRetrieved
                            } else {
                                PeopleMviIntent.PeoplePageRetrieved(peoplePage)
                            }
                        }
                    },
                    onFailure = {
                        PeopleMviIntent.PeopleError
                    }
                )
            }
            PeopleMviIntent.PeopleEmptyPageRetrieved -> {
                PeopleMviIntent.LoadMorePeople
            }
            else -> null
        }
    }
}