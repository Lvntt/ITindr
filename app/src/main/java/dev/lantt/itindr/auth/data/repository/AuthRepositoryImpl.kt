package dev.lantt.itindr.auth.data.repository

import dev.lantt.itindr.auth.data.api.AuthApiService
import dev.lantt.itindr.auth.domain.entity.RegisterBody
import dev.lantt.itindr.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApiService: AuthApiService
) : AuthRepository {
    override suspend fun register(registerBody: RegisterBody) {
        // TODO save tokens
        authApiService.register(registerBody)
    }
}