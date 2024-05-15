package dev.lantt.itindr.auth.domain.entity

data class RegisterBody(
    val email: String,
    val password: String
)