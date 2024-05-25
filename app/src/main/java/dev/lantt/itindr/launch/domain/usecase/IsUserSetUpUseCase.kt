package dev.lantt.itindr.launch.domain.usecase

import dev.lantt.itindr.profile.domain.repository.ProfileRepository

class IsUserSetUpUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(): Boolean {
        val profile = profileRepository.getProfile()
        return profile.name.isNotBlank()
    }

}