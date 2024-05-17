package dev.lantt.itindr.auth.common.domain.repository

import dev.lantt.itindr.auth.register.domain.entity.RegisterBody

interface AuthRepository {
    suspend fun register(registerBody: RegisterBody)
    suspend fun refresh(refreshToken: String)
}