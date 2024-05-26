package dev.lantt.itindr.feed.domain.usecase

import dev.lantt.itindr.feed.domain.repository.UserRepository

class DislikeUserUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userId: String) = userRepository.dislike(userId)

}