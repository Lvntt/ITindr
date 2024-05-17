package dev.lantt.itindr.auth.presentation.store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lantt.itindr.auth.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.presentation.state.RegisterMviState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerMiddleware: RegisterMiddleware,
    private val registerReducer: RegisterReducer,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private companion object {
        const val TAG = "RegisterViewModel"
    }

    private val stateFlow = MutableStateFlow(RegisterMviState())
    val state = stateFlow.asStateFlow()

    private val effectsFlow = MutableSharedFlow<RegisterMviEffect>()
    val effects = effectsFlow.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.stackTraceToString())
        sendEffect(RegisterMviEffect.ShowError)
    }

    init {
        registerReducer.effects
            .onEach(::sendEffect)
            .launchIn(viewModelScope)
    }

    fun dispatch(intent: RegisterMviIntent) {
        viewModelScope.launch(defaultDispatcher + exceptionHandler) {
            stateFlow.update {
                registerReducer.reduce(stateFlow.value, intent)
            }
            registerMiddleware.resolve(stateFlow.value, intent)?.let {
                dispatch(it)
            }
        }
    }

    private fun sendEffect(effect: RegisterMviEffect) {
        viewModelScope.launch {
            effectsFlow.emit(effect)
        }
    }

}