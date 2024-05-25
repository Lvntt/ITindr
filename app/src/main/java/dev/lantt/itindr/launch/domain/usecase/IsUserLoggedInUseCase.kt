package dev.lantt.itindr.launch.domain.usecase

import dev.lantt.itindr.core.domain.repository.AuthRepository

class IsUserLoggedInUseCase(
    private val authRepository: AuthRepository
) {

    operator fun invoke() = authRepository.isUserLoggedIn()

}