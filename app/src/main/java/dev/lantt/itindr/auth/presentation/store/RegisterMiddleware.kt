package dev.lantt.itindr.auth.presentation.store

import dev.lantt.itindr.auth.domain.entity.RegisterBody
import dev.lantt.itindr.auth.domain.entity.ValidationError
import dev.lantt.itindr.auth.domain.usecase.RegisterUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidateEmailUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidatePasswordUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidateRepeatedPasswordUseCase
import dev.lantt.itindr.auth.presentation.mapper.ValidationErrorToStringRes
import dev.lantt.itindr.auth.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.presentation.state.RegisterMviState

class RegisterMiddleware(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
) {
    suspend fun resolve(state: RegisterMviState, intent: RegisterMviIntent): RegisterMviIntent? {
        return when (intent) {
            is RegisterMviIntent.EmailChanged -> {
                val validationError = validateEmailUseCase(intent.email)
                val emailErrorResId = getErrorStringRes(validationError)
                RegisterMviIntent.EmailValidated(emailErrorResId)
            }
            is RegisterMviIntent.PasswordChanged -> {
                val passwordValidationError = validatePasswordUseCase(intent.password)
                val passwordErrorResId = getErrorStringRes(passwordValidationError)
                val repeatedPasswordValidationError = validateRepeatedPasswordUseCase(intent.password, state.repeatedPassword)
                val repeatedPasswordErrorResId = getErrorStringRes(repeatedPasswordValidationError)
                RegisterMviIntent.PasswordValidated(passwordErrorResId, repeatedPasswordErrorResId)
            }
            is RegisterMviIntent.RepeatedPasswordChanged -> {
                val validationError = validateRepeatedPasswordUseCase(state.password, intent.repeatedPassword)
                val repeatedPasswordErrorResId = getErrorStringRes(validationError)
                RegisterMviIntent.RepeatedPasswordValidated(repeatedPasswordErrorResId)
            }
            RegisterMviIntent.RegisterRequested -> {
                runCatching {
                    registerUseCase(
                        RegisterBody(
                            email = state.email,
                            password = state.password
                        )
                    )
                }.fold(
                    onSuccess = {
                        RegisterMviIntent.RegisterSuccessful
                    },
                    onFailure = {
                        RegisterMviIntent.RegisterFailed
                    }
                )
            }
            else -> null
        }
    }

    private fun getErrorStringRes(error: ValidationError?): Int? {
        return ValidationErrorToStringRes.stringResources[error]
    }
}