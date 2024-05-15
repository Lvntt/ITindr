package dev.lantt.itindr.auth.domain.entity

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
