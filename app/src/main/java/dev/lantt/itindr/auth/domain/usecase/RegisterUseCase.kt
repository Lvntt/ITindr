package dev.lantt.itindr.auth.domain.usecase

import dev.lantt.itindr.auth.domain.entity.RegisterBody
import dev.lantt.itindr.auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(registerBody: RegisterBody) =
        authRepository.register(registerBody)

}