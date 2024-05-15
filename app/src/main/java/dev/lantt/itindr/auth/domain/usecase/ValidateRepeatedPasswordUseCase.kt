package dev.lantt.itindr.auth.domain.usecase

import dev.lantt.itindr.auth.domain.entity.ValidationError

class ValidateRepeatedPasswordUseCase {

    operator fun invoke(password: String, repeatedPassword: String): ValidationError? {
        if (password != repeatedPassword) {
            return ValidationError.PASSWORDS_MISMATCH
        }
        return null
    }

}