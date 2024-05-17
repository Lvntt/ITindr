package dev.lantt.itindr.auth.register.domain.usecase

import dev.lantt.itindr.auth.common.domain.entity.ValidationError

class ValidateRepeatedPasswordUseCase {

    operator fun invoke(password: String, repeatedPassword: String): ValidationError? {
        if (password != repeatedPassword) {
            return ValidationError.PASSWORDS_MISMATCH
        }
        return null
    }

}