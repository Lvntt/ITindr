package dev.lantt.itindr.feed.domain.usecase

import dev.lantt.itindr.feed.domain.repository.UserRepository
import dev.lantt.itindr.profile.domain.entity.Profile

class GetFeedUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): List<Profile> {
        return userRepository.getFeed().filter { it.name.isNotBlank() }
    }

}