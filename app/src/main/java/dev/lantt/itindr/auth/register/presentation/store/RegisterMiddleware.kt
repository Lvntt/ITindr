package dev.lantt.itindr.auth.register.presentation.store

import dev.lantt.itindr.auth.register.domain.entity.RegisterBody
import dev.lantt.itindr.auth.common.domain.entity.ValidationError
import dev.lantt.itindr.auth.register.domain.usecase.RegisterUseCase
import dev.lantt.itindr.auth.common.domain.usecase.ValidateEmailUseCase
import dev.lantt.itindr.auth.common.domain.usecase.ValidatePasswordUseCase
import dev.lantt.itindr.auth.register.domain.usecase.ValidateRepeatedPasswordUseCase
import dev.lantt.itindr.auth.register.presentation.mapper.ValidationErrorToStringRes
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviState
import dev.lantt.itindr.core.presentation.mvi.Middleware

class RegisterMiddleware(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
) : Middleware<RegisterMviState, RegisterMviIntent> {
    override suspend fun resolve(state: RegisterMviState, intent: RegisterMviIntent): RegisterMviIntent? {
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