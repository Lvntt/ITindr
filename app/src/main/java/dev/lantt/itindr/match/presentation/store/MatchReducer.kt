package dev.lantt.itindr.match.presentation.store

import dev.lantt.itindr.core.presentation.mvi.Reducer
import dev.lantt.itindr.match.presentation.state.MatchMviEffect
import dev.lantt.itindr.match.presentation.state.MatchMviIntent
import dev.lantt.itindr.match.presentation.state.MatchMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MatchReducer : Reducer<MatchMviState, MatchMviIntent, MatchMviEffect> {

    private val effectsFlow = MutableSharedFlow<MatchMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<MatchMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(state: MatchMviState, intent: MatchMviIntent): MatchMviState {
        return when (intent) {
            is MatchMviIntent.InitUserId -> state.copy(userId = intent.userId)
            MatchMviIntent.CreateChat -> state.copy(isLoading = true)
            is MatchMviIntent.ChatCreated -> {
                sendEffect(MatchMviEffect.CreateChat(intent.uiChat))
                state.copy(isLoading = false)
            }
            MatchMviIntent.NetworkError -> {
                sendEffect(MatchMviEffect.ShowError)
                state.copy(isLoading = false)
            }
        }
    }

    private fun sendEffect(effect: MatchMviEffect) {
        effectsFlow.tryEmit(effect)
    }
}