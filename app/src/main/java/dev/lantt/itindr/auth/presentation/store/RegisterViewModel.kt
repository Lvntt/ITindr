package dev.lantt.itindr.auth.presentation.store

import dev.lantt.itindr.auth.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.presentation.state.RegisterMviState
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