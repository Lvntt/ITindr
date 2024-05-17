package dev.lantt.itindr.auth.domain.repository

import dev.lantt.itindr.auth.domain.entity.RegisterBody

interface AuthRepository {
    suspend fun register(registerBody: RegisterBody)
    suspend fun refresh(refreshToken: String)
}