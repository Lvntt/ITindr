package dev.lantt.itindr.auth.register.presentation.store

import dev.lantt.itindr.auth.register.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviState
import dev.lantt.itindr.core.presentation.mvi.MviStore
import kotlinx.coroutines.CoroutineDispatcher

class RegisterViewModel(
    middleware: RegisterMiddleware,
    reducer: RegisterReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<RegisterMviState, RegisterMviIntent, RegisterMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): RegisterMviState = RegisterMviState()
}