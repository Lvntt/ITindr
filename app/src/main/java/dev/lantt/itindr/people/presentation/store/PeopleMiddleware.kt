package dev.lantt.itindr.people.presentation.store

import dev.lantt.itindr.core.constants.Constants.INITIAL_PAGE_SIZE
import dev.lantt.itindr.core.constants.Constants.PEOPLE_PAGE_LIMIT
import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.people.presentation.domain.usecase.GetInitialUsersUseCase
import dev.lantt.itindr.people.presentation.domain.usecase.GetUserListUseCase
import dev.lantt.itindr.people.presentation.state.PeopleMviIntent
import dev.lantt.itindr.people.presentation.state.PeopleMviState
import dev.lantt.itindr.profile.domain.entity.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PeopleMiddleware(
    private val getUserListUseCase: GetUserListUseCase,
    private val getInitialUsersUseCase: GetInitialUsersUseCase,
) : Middleware<PeopleMviState, PeopleMviIntent> {
    override fun resolve(state: PeopleMviState, intent: PeopleMviIntent): Flow<PeopleMviIntent>? {
        return when (intent) {
            PeopleMviIntent.LoadMorePeople -> flow {
                runCatching {
                    getUserListUseCase(
                        PEOPLE_PAGE_LIMIT,
                        INITIAL_PAGE_SIZE + state.currentPage * PEOPLE_PAGE_LIMIT
                    )
                }.fold(
                    onSuccess = { remotePeople ->
                        if (remotePeople.isEmpty()) {
                            emit(PeopleMviIntent.PeopleEnded)
                        } else {
                            val peoplePage = remotePeople.filter { it.name.isNotBlank() }
                            if (peoplePage.isEmpty()) {
                                emit(PeopleMviIntent.PeopleEmptyPageRetrieved)
                            } else {
                                emit(PeopleMviIntent.PeoplePageRetrieved(peoplePage))
                            }
                        }
                    },
                    onFailure = {
                        emit(PeopleMviIntent.PeopleError)
                    }
                )
            }

            PeopleMviIntent.PeopleEmptyPageRetrieved -> flow {
                emit(PeopleMviIntent.LoadMorePeople)
            }

            PeopleMviIntent.LoadInitialPeople ->
                    getInitialUsersUseCase()
                        .map<List<Profile>, PeopleMviIntent> { initialUsers ->
                            val users = initialUsers.filter { it.name.isNotBlank() }
                            PeopleMviIntent.ReplacePeople(users)
                        }
                        .catch { emit(PeopleMviIntent.PeopleError) }

            else -> null
        }
    }
}