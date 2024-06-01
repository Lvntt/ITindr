package dev.lantt.itindr.people.presentation.domain.usecase

import dev.lantt.itindr.feed.domain.repository.UserRepository

class GetUserListUseCase(
   private val userRepository: UserRepository
) {

    suspend operator fun invoke(limit: Int, offset: Int) = userRepository.getUsers(limit, offset)

}