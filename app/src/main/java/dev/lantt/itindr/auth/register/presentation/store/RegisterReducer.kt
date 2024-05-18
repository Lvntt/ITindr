package dev.lantt.itindr.auth.register.presentation.store

import dev.lantt.itindr.auth.register.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviState
import dev.lantt.itindr.core.presentation.mvi.Reducer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class RegisterReducer : Reducer<RegisterMviState, RegisterMviIntent, RegisterMviEffect> {

    private val effectsFlow = MutableSharedFlow<RegisterMviEffect>(extraBufferCapacity = 1)
    override val effects = effectsFlow.asSharedFlow()

    override fun reduce(state: RegisterMviState, intent: RegisterMviIntent): RegisterMviState {
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

    private fun sendEffect(effect: RegisterMviEffect) {
        effectsFlow.tryEmit(effect)
    }
}