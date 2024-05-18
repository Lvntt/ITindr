package dev.lantt.itindr.auth.register.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviState

data class RegisterMviState(
    val email: String = "",
    val emailErrorResId: Int? = null,
    val password: String = "",
    val passwordErrorResId: Int? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordErrorResId: Int? = null,
    val registrationState: RegistrationState = RegistrationState.IDLE
) : MviState {
    val isRegistrationAllowed: Boolean =
        emailErrorResId == null &&
        passwordErrorResId == null &&
        repeatedPasswordErrorResId == null &&
        email.isNotBlank() &&
        password.isNotBlank() &&
        repeatedPassword.isNotBlank()

    enum class RegistrationState {
        IDLE,
        LOADING
    }
}
