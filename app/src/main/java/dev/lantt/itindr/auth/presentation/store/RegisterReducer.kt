package dev.lantt.itindr.auth.presentation.store

import dev.lantt.itindr.auth.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.presentation.state.RegisterMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class RegisterReducer {
    private val effectsFlow = MutableSharedFlow<RegisterMviEffect>()
    val effects = effectsFlow.asSharedFlow()

    suspend fun reduce(state: RegisterMviState, intent: RegisterMviIntent): RegisterMviState {
        return when (intent) {
            is RegisterMviIntent.EmailChanged -> state.copy(email = intent.email)
            is RegisterMviIntent.PasswordChanged -> state.copy(password = intent.password)
            is RegisterMviIntent.RepeatedPasswordChanged -> state.copy(repeatedPassword = intent.repeatedPassword)
            RegisterMviIntent.RegisterRequested -> state.copy(registrationState = RegisterMviState.RegistrationState.LOADING)
            RegisterMviIntent.Back -> {
                sendEffect(RegisterMviEffect.GoToPreviousScreen)
                state
            }
            is RegisterMviIntent.EmailValidated -> state.copy(emailErrorResId = intent.errorStringResId)
            is RegisterMviIntent.PasswordValidated -> state.copy(
                passwordErrorResId = intent.passwordErrorStringResId,
                repeatedPasswordErrorResId = intent.repeatedPasswordErrorStringResId
            )
            is RegisterMviIntent.RepeatedPasswordValidated -> state.copy(repeatedPasswordErrorResId = intent.errorStringResId)
            RegisterMviIntent.RegisterSuccessful -> {
                sendEffect(RegisterMviEffect.GoToAboutYourselfScreen)
                state.copy(registrationState = RegisterMviState.RegistrationState.IDLE)
            }
            RegisterMviIntent.RegisterFailed -> {
                sendEffect(RegisterMviEffect.ShowError)
                state.copy(registrationState = RegisterMviState.RegistrationState.IDLE)
            }
        }
    }

    private suspend fun sendEffect(effect: RegisterMviEffect) {
        effectsFlow.emit(effect)
    }
}