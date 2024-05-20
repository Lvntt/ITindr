package dev.lantt.itindr.core.domain.repository

import dev.lantt.itindr.auth.login.domain.entity.LoginBody
import dev.lantt.itindr.auth.register.domain.entity.RegisterBody
import dev.lantt.itindr.core.data.model.TokenResponse

interface AuthRepository {
    suspend fun register(registerBody: RegisterBody)
    suspend fun login(loginBody: LoginBody)
    suspend fun refresh(): TokenResponse
}