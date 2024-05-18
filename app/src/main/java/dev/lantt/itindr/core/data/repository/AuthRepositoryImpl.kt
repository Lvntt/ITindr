package dev.lantt.itindr.core.data.repository

import dev.lantt.itindr.auth.login.domain.entity.LoginBody
import dev.lantt.itindr.core.data.api.AuthApiService
import dev.lantt.itindr.core.data.datasource.SessionManager
import dev.lantt.itindr.core.data.model.RefreshTokenBody
import dev.lantt.itindr.auth.register.domain.entity.RegisterBody
import dev.lantt.itindr.core.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val sessionManager: SessionManager
) : AuthRepository {
    override suspend fun register(registerBody: RegisterBody) {
        val tokenResponse = authApiService.register(registerBody)
        sessionManager.saveTokens(tokenResponse)
    }

    override suspend fun login(loginBody: LoginBody) {
        val tokenResponse = authApiService.login(loginBody)
        sessionManager.saveTokens(tokenResponse)
    }

    override suspend fun refresh(refreshToken: String) {
        val tokenResponse = authApiService.refresh(RefreshTokenBody(refreshToken))
        sessionManager.saveTokens(tokenResponse)
    }
}