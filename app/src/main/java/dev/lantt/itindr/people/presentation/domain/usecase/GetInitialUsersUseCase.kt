package dev.lantt.itindr.people.presentation.domain.usecase

import dev.lantt.itindr.feed.domain.repository.UserRepository

class GetInitialUsersUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.getInitialUsers()

}