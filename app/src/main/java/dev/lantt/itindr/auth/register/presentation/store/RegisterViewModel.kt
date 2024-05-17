package dev.lantt.itindr.auth.register.presentation.store

import dev.lantt.itindr.auth.register.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviState
import dev.lantt.itindr.core.presentation.mvi.MviStore
import kotlinx.coroutines.CoroutineDispatcher

class RegisterViewModel(
    registerMiddleware: RegisterMiddleware,
    registerReducer: RegisterReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<RegisterMviState, RegisterMviIntent, RegisterMviEffect>(
    registerMiddleware, registerReducer, defaultDispatcher
) {
    override fun initialStateProvider(): RegisterMviState = RegisterMviState()
}