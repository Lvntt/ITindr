package dev.lantt.itindr.profile.domain.usecase

import dev.lantt.itindr.profile.domain.repository.ProfileRepository

class SaveProfileLocallyUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke() = profileRepository.saveProfileLocally()

}