package dev.lantt.itindr.auth.register.presentation.mapper

import dev.lantt.itindr.R
import dev.lantt.itindr.auth.common.domain.entity.ValidationError

object ValidationErrorToStringRes {
    val stringResources = mapOf(
        ValidationError.EMPTY_FIELD to R.string.emptyField,
        ValidationError.INVALID_EMAIL to R.string.invalidEmail,
        ValidationError.PASSWORD_TOO_SHORT to R.string.passwordTooShort,
        ValidationError.PASSWORDS_MISMATCH to R.string.passwordsMismatch,
    )
}