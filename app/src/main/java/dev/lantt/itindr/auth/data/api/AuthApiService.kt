package dev.lantt.itindr.auth.data.api

import dev.lantt.itindr.auth.domain.entity.RegisterBody
import dev.lantt.itindr.auth.domain.entity.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(REGISTER_URL)
    suspend fun register(@Body registerBody: RegisterBody): TokenResponse

    private companion object {
        const val REGISTER_URL = "v1/auth/register"
    }
}