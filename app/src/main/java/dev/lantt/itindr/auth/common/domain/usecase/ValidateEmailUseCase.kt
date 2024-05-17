package dev.lantt.itindr.auth.common.domain.usecase

import dev.lantt.itindr.auth.common.domain.entity.ValidationError

class ValidateEmailUseCase {

    private companion object {
        val emailRegex = Regex("\\w+@\\w+\\.\\w+")
    }

    operator fun invoke(email: String): ValidationError? {
        return when {
            email.isBlank() -> ValidationError.EMPTY_FIELD
            !emailRegex.matches(email) -> ValidationError.INVALID_EMAIL
            else -> null
        }
    }

}