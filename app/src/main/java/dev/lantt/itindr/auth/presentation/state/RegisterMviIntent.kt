package dev.lantt.itindr.auth.presentation.state

sealed interface RegisterMviIntent {

    data class EmailChanged(val email: String) : RegisterMviIntent
    data class PasswordChanged(val password: String) : RegisterMviIntent
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegisterMviIntent
    data object RegisterRequested : RegisterMviIntent
    data object Back : RegisterMviIntent

    data class EmailValidated(val errorStringResId: Int?) : RegisterMviIntent
    data class PasswordValidated(
        val passwordErrorStringResId: Int?,
        val repeatedPasswordErrorStringResId: Int?
    ) : RegisterMviIntent
    data class RepeatedPasswordValidated(val errorStringResId: Int?) : RegisterMviIntent
    data object RegisterSuccessful : RegisterMviIntent
    data object RegisterFailed : RegisterMviIntent
}