package dev.lantt.itindr.auth.common.domain.entity

enum class ValidationError {
    EMPTY_FIELD,
    INVALID_EMAIL,
    PASSWORD_TOO_SHORT,
    PASSWORDS_MISMATCH
}