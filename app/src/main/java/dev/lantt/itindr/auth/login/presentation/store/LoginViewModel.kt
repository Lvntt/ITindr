package dev.lantt.itindr.auth.login.presentation.store

import dev.lantt.itindr.auth.login.presentation.state.LoginMviEffect
import dev.lantt.itindr.auth.login.presentation.state.LoginMviIntent
import dev.lantt.itindr.auth.login.presentation.state.LoginMviState
import dev.lantt.itindr.core.presentation.mvi.MviStore
import kotlinx.coroutines.CoroutineDispatcher

class LoginViewModel(
    middleware: LoginMiddleware,
    reducer: LoginReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<LoginMviState, LoginMviIntent, LoginMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): LoginMviState = LoginMviState()
}