package dev.lantt.itindr.core.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MviStore<State : MviState, Intent : MviIntent, Effect : MviEffect>(
    private val middleware: Middleware<State, Intent>,
    private val reducer: Reducer<State, Intent, Effect>,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    abstract fun initialStateProvider(): State
    private val initialState: State by lazy { initialStateProvider() }

    private val stateFlow: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = stateFlow.asStateFlow()

    private val effectsFlow: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = effectsFlow.asSharedFlow()

    init {
        reducer.effects
            .onEach(::sendEffect)
            .launchIn(viewModelScope)
    }

    fun dispatch(intent: Intent) {
        stateFlow.update {
            reducer.reduce(stateFlow.value, intent)
        }
        middleware
            .resolve(stateFlow.value, intent)
            ?.onEach(::dispatch)
            ?.launchIn(viewModelScope)
    }

    private fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            effectsFlow.emit(effect)
        }
    }
}