package dev.lantt.itindr.auth.register.domain.usecase

import dev.lantt.itindr.auth.register.domain.entity.RegisterBody
import dev.lantt.itindr.auth.common.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(registerBody: RegisterBody) =
        authRepository.register(registerBody)

}