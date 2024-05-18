package dev.lantt.itindr.auth.login.presentation.store

import dev.lantt.itindr.auth.common.domain.entity.ValidationError
import dev.lantt.itindr.auth.common.domain.usecase.ValidateEmailUseCase
import dev.lantt.itindr.auth.common.domain.usecase.ValidatePasswordUseCase
import dev.lantt.itindr.auth.login.domain.entity.LoginBody
import dev.lantt.itindr.auth.login.domain.usecase.LoginUseCase
import dev.lantt.itindr.auth.login.presentation.state.LoginMviIntent
import dev.lantt.itindr.auth.login.presentation.state.LoginMviState
import dev.lantt.itindr.auth.register.presentation.mapper.ValidationErrorToStringRes
import dev.lantt.itindr.core.presentation.mvi.Middleware

class LoginMiddleware(
    private val loginUseCase: LoginUseCase,
    private val validateLoginUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : Middleware<LoginMviState, LoginMviIntent> {
    override suspend fun resolve(state: LoginMviState, intent: LoginMviIntent): LoginMviIntent? {
        return when (intent) {
            is LoginMviIntent.EmailChanged -> {
                val validationError = validateLoginUseCase(intent.email)
                val emailErrorResId = getErrorStringRes(validationError)
                return LoginMviIntent.EmailValidated(emailErrorResId)
            }
            is LoginMviIntent.PasswordChanged -> {
                val validationError = validatePasswordUseCase(intent.password)
                val passwordErrorResId = getErrorStringRes(validationError)
                return LoginMviIntent.EmailValidated(passwordErrorResId)
            }
            LoginMviIntent.LoginRequested -> {
                runCatching {
                    loginUseCase(
                        LoginBody(
                            email = state.email,
                            password = state.password
                        )
                    )
                }.fold(
                    onSuccess = {
                        LoginMviIntent.LoginSuccessful
                    },
                    onFailure = {
                        LoginMviIntent.LoginFailed
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