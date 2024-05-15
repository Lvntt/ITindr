package dev.lantt.itindr.auth.domain.repository

import dev.lantt.itindr.auth.domain.entity.RegisterBody
import dev.lantt.itindr.auth.domain.entity.TokenResponse

interface AuthRepository {
    suspend fun register(registerBody: RegisterBody): TokenResponse
}