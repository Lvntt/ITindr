package dev.lantt.itindr.core.data.model

data class TokenResponse(
    val accessToken: String,
    val accessTokenExpiredAt: Long,
    val refreshToken: String,
    val refreshTokenExpiredAt: Long
)