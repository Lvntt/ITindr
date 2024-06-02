package dev.lantt.itindr.auth.register.presentation.store

import dev.lantt.itindr.auth.common.domain.entity.ValidationError
import dev.lantt.itindr.auth.common.domain.usecase.ValidateEmailUseCase
import dev.lantt.itindr.auth.common.domain.usecase.ValidatePasswordUseCase
import dev.lantt.itindr.auth.register.domain.entity.RegisterBody
import dev.lantt.itindr.auth.register.domain.usecase.RegisterUseCase
import dev.lantt.itindr.auth.register.domain.usecase.ValidateRepeatedPasswordUseCase
import dev.lantt.itindr.auth.register.presentation.mapper.ValidationErrorToStringRes
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviState
import dev.lantt.itindr.core.presentation.mvi.Middleware
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterMiddleware(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
) : Middleware<RegisterMviState, RegisterMviIntent> {
    override fun resolve(state: RegisterMviState, intent: RegisterMviIntent): Flow<RegisterMviIntent>? {
        return when (intent) {
            is RegisterMviIntent.EmailChanged -> flow {
                val validationError = validateEmailUseCase(intent.email)
                val emailErrorResId = getErrorStringRes(validationError)
                emit(RegisterMviIntent.EmailValidated(emailErrorResId))
            }
            is RegisterMviIntent.PasswordChanged -> flow {
                val passwordValidationError = validatePasswordUseCase(intent.password)
                val passwordErrorResId = getErrorStringRes(passwordValidationError)
                val repeatedPasswordValidationError = validateRepeatedPasswordUseCase(intent.password, state.repeatedPassword)
                val repeatedPasswordErrorResId = getErrorStringRes(repeatedPasswordValidationError)
                emit(RegisterMviIntent.PasswordValidated(passwordErrorResId, repeatedPasswordErrorResId))
            }
            is RegisterMviIntent.RepeatedPasswordChanged -> flow {
                val validationError = validateRepeatedPasswordUseCase(state.password, intent.repeatedPassword)
                val repeatedPasswordErrorResId = getErrorStringRes(validationError)
                emit(RegisterMviIntent.RepeatedPasswordValidated(repeatedPasswordErrorResId))
            }
            RegisterMviIntent.RegisterRequested -> flow {
                runCatching {
                    registerUseCase(
                        RegisterBody(
                            email = state.email,
                            password = state.password
                        )
                    )
                }.fold(
                    onSuccess = {
                        emit(RegisterMviIntent.RegisterSuccessful)
                    },
                    onFailure = {
                        emit(RegisterMviIntent.RegisterFailed)
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