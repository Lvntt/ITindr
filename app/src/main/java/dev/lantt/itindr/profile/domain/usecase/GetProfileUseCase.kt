package dev.lantt.itindr.profile.domain.usecase

import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(): Profile = profileRepository.getProfile()

}