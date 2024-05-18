package dev.lantt.itindr.auth.login.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviState

data class LoginMviState(
    val email: String = "",
    val emailErrorResId: Int? = null,
    val password: String = "",
    val passwordErrorResId: Int? = null,
    val loginState: LoginState = LoginState.IDLE
) : MviState {
    val isLoginAllowed: Boolean =
        emailErrorResId == null &&
        passwordErrorResId == null &&
        email.isNotBlank() &&
        password.isNotBlank()

    enum class LoginState {
        IDLE,
        LOADING
    }
}
