package dev.lantt.itindr.launch.domain.usecase

import dev.lantt.itindr.core.domain.repository.AuthRepository
import dev.lantt.itindr.profile.domain.repository.ProfileRepository

class IsUserSetUpUseCase(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Boolean {
        try {
            val profile = profileRepository.getProfile()
            return profile.name.isNotBlank()
        } catch (e: Exception) {
            return authRepository.isUserSetUp()
        }
    }

}