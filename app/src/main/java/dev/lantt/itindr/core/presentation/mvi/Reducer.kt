package dev.lantt.itindr.core.presentation.mvi

import kotlinx.coroutines.flow.SharedFlow

interface Reducer<State : MviState, Intent : MviIntent, Effect : MviEffect> {

    val effects: SharedFlow<Effect>

    fun reduce(state: State, intent: Intent): State
}