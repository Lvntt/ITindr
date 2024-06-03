package dev.lantt.itindr.profile.domain.usecase

import dev.lantt.itindr.core.domain.repository.AuthRepository

class SaveIsSetUpUseCase(
    private val authRepository: AuthRepository
) {

    operator fun invoke() = authRepository.saveIsSetUp()

}