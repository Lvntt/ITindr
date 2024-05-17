package dev.lantt.itindr.auth.common.domain.usecase

import dev.lantt.itindr.auth.common.domain.entity.ValidationError

class ValidatePasswordUseCase {

    private companion object {
        const val MIN_LENGTH = 8
    }

    operator fun invoke(password: String): ValidationError? {
        return when {
            password.isBlank() -> ValidationError.EMPTY_FIELD
            password.length < MIN_LENGTH -> ValidationError.PASSWORD_TOO_SHORT
            else -> null
        }
    }

}