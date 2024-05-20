package dev.lantt.itindr.core.data.interceptor

import dev.lantt.itindr.core.constants.Constants.NO_CONTENT_CODE
import dev.lantt.itindr.core.constants.Constants.SUCCESS_CODE
import dev.lantt.itindr.core.data.datasource.SessionManager
import dev.lantt.itindr.core.data.model.TokenType
import dev.lantt.itindr.core.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authRepository: AuthRepository,
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
                    val newTokens = authRepository.refresh()
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

        val response = chain.proceed(authorizedRequest)
        if (response.code == NO_CONTENT_CODE) {
            return response
                .newBuilder()
                .code(SUCCESS_CODE)
                .build()
        }
        return response
    }
}