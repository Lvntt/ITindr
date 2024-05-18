package dev.lantt.itindr.auth.login.domain.usecase

import dev.lantt.itindr.auth.login.domain.entity.LoginBody
import dev.lantt.itindr.core.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(loginBody: LoginBody) =
        authRepository.login(loginBody)

}