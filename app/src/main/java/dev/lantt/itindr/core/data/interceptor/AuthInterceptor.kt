package dev.lantt.itindr.core.data.interceptor

import dev.lantt.itindr.core.data.api.AuthApiService
import dev.lantt.itindr.core.data.datasource.SessionManager
import dev.lantt.itindr.core.data.model.RefreshTokenBody
import dev.lantt.itindr.core.data.model.TokenType
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authApiService: AuthApiService,
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = sessionManager.fetchToken(TokenType.ACCESS)
        val isAccessTokenExpired = sessionManager.isTokenExpired(TokenType.ACCESS)

        if (accessToken != null && isAccessTokenExpired) {
            val refreshToken = sessionManager.fetchToken(TokenType.REFRESH)
            val newAccessToken = runBlocking {
                refreshToken?.let {
                    val newTokens = authApiService.refresh(RefreshTokenBody(it))
                    sessionManager.saveTokens(newTokens)
                    newTokens.accessToken
                }
            }

            if (newAccessToken != null) {
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()

                return chain.proceed(newRequest)
            }
        }

        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(authorizedRequest)
    }
}