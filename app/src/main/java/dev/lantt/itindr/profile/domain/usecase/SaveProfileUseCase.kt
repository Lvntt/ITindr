package dev.lantt.itindr.profile.domain.usecase

import dev.lantt.itindr.profile.domain.entity.UpdateProfileBody
import dev.lantt.itindr.profile.domain.repository.ProfileRepository

class SaveProfileUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(profile: UpdateProfileBody) =
        profileRepository.saveProfile(profile)

}