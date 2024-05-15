package dev.lantt.itindr.auth.presentation.state

sealed interface RegisterMviIntent {

    data class EmailChanged(val email: String) : RegisterMviIntent
    data class PasswordChanged(val password: String) : RegisterMviIntent
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegisterMviIntent
    data object Register : RegisterMviIntent
    data object Back : RegisterMviIntent
}