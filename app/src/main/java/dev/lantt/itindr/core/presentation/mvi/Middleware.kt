package dev.lantt.itindr.core.presentation.mvi

interface Middleware<State : MviState, Intent : MviIntent> {

    suspend fun resolve(state: State, intent: Intent): Intent?
}