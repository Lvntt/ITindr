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
import dev.lantt.itindr.launch.domain.usecase.IsUserSetUpUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginMiddleware(
    private val loginUseCase: LoginUseCase,
    private val validateLoginUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val isUserSetUpUseCase: IsUserSetUpUseCase
) : Middleware<LoginMviState, LoginMviIntent> {
    override fun resolve(state: LoginMviState, intent: LoginMviIntent): Flow<LoginMviIntent>? {
        return when (intent) {
            is LoginMviIntent.EmailChanged -> flow {
                val validationError = validateLoginUseCase(intent.email)
                val emailErrorResId = getErrorStringRes(validationError)
                emit(LoginMviIntent.EmailValidated(emailErrorResId))
            }
            is LoginMviIntent.PasswordChanged -> flow {
                val validationError = validatePasswordUseCase(intent.password)
                val passwordErrorResId = getErrorStringRes(validationError)
                emit(LoginMviIntent.PasswordValidated(passwordErrorResId))
            }
            LoginMviIntent.LoginRequested -> flow {
                try {
                    loginUseCase(
                        LoginBody(
                            email = state.email,
                            password = state.password
                        )
                    )

                    if (isUserSetUpUseCase()) {
                        emit(LoginMviIntent.UserIsSetUp)
                    } else {
                        emit(LoginMviIntent.LoginSuccessful)
                    }
                } catch (e: Exception) {
                    emit(LoginMviIntent.LoginFailed)
                }
            }
            else -> null
        }
    }

    private fun getErrorStringRes(error: ValidationError?): Int? {
        return ValidationErrorToStringRes.stringResources[error]
    }
}