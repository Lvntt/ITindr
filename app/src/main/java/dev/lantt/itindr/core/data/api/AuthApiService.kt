package dev.lantt.itindr.core.data.api

import dev.lantt.itindr.core.data.model.RefreshTokenBody
import dev.lantt.itindr.auth.domain.entity.RegisterBody
import dev.lantt.itindr.core.data.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(REGISTER_URL)
    suspend fun register(@Body registerBody: RegisterBody): TokenResponse

    @POST(REFRESH_URL)
    suspend fun refresh(@Body refreshToken: RefreshTokenBody): TokenResponse

    private companion object {
        const val REGISTER_URL = "v1/auth/register"
        const val REFRESH_URL = "v1/auth/refresh"
    }
}