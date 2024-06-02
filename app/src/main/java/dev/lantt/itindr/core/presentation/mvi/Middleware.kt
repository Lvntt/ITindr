package dev.lantt.itindr.core.presentation.mvi

import kotlinx.coroutines.flow.Flow

interface Middleware<State : MviState, Intent : MviIntent> {

    fun resolve(state: State, intent: Intent): Flow<Intent>?
}