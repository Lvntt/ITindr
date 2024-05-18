package dev.lantt.itindr.auth.login.presentation.store

import dev.lantt.itindr.auth.login.presentation.state.LoginMviEffect
import dev.lantt.itindr.auth.login.presentation.state.LoginMviIntent
import dev.lantt.itindr.auth.login.presentation.state.LoginMviState
import dev.lantt.itindr.core.presentation.mvi.Reducer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LoginReducer : Reducer<LoginMviState, LoginMviIntent, LoginMviEffect> {

    private val effectsFlow = MutableSharedFlow<LoginMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<LoginMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(state: LoginMviState, intent: LoginMviIntent): LoginMviState {
        return when (intent) {
            is LoginMviIntent.EmailChanged -> state.copy(email = intent.email)
            is LoginMviIntent.PasswordChanged -> state.copy(password = intent.password)
            is LoginMviIntent.EmailValidated -> state.copy(emailErrorResId = intent.errorStringResId)
            is LoginMviIntent.PasswordValidated -> state.copy(passwordErrorResId = intent.errorStringResId)
            LoginMviIntent.LoginRequested -> state.copy(loginState = LoginMviState.LoginState.LOADING)
            LoginMviIntent.Back -> {
                sendEffect(LoginMviEffect.GoToPreviousScreen)
                state
            }
            LoginMviIntent.LoginFailed -> {
                sendEffect(LoginMviEffect.ShowError)
                state.copy(loginState = LoginMviState.LoginState.IDLE)
            }
            LoginMviIntent.LoginSuccessful -> {
                sendEffect(LoginMviEffect.GoToFeedScreen)
                state.copy(loginState = LoginMviState.LoginState.IDLE)
            }
        }
    }

    private fun sendEffect(effect: LoginMviEffect) {
        effectsFlow.tryEmit(effect)
    }

}