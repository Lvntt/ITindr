package dev.lantt.itindr.auth.login.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviIntent

sealed interface LoginMviIntent : MviIntent {

    data class EmailChanged(val email: String) : LoginMviIntent
    data class PasswordChanged(val password: String) : LoginMviIntent
    data object LoginRequested : LoginMviIntent
    data object Back : LoginMviIntent

    data class EmailValidated(val errorStringResId: Int?) : LoginMviIntent
    data class PasswordValidated(val errorStringResId: Int?) : LoginMviIntent
    data object LoginSuccessful : LoginMviIntent
    data object UserIsSetUp : LoginMviIntent
    data object LoginFailed : LoginMviIntent
}