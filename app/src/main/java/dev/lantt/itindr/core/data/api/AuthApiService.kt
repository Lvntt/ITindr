package dev.lantt.itindr.core.data.api

import dev.lantt.itindr.auth.login.domain.entity.LoginBody
import dev.lantt.itindr.core.data.model.RefreshTokenBody
import dev.lantt.itindr.auth.register.domain.entity.RegisterBody
import dev.lantt.itindr.core.data.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

const val REGISTER_URL = "v1/auth/register"
const val LOGIN_URL = "v1/auth/login"
const val REFRESH_URL = "v1/auth/refresh"

interface AuthApiService {

    @POST(REGISTER_URL)
    suspend fun register(@Body registerBody: RegisterBody): TokenResponse

    @POST(LOGIN_URL)
    suspend fun login(@Body loginBody: LoginBody): TokenResponse

    @POST(REFRESH_URL)
    suspend fun refresh(
        @Header("Authorization") expiredToken: String,
        @Body refreshToken: RefreshTokenBody
    ): TokenResponse

}