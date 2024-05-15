package dev.lantt.itindr.auth.domain.entity

enum class ValidationError {
    EMPTY_FIELD,
    INVALID_EMAIL,
    PASSWORD_TOO_SHORT,
    PASSWORDS_MISMATCH
}