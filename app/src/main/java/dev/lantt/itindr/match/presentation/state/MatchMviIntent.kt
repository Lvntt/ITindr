package dev.lantt.itindr.match.presentation.state

import dev.lantt.itindr.chats.chat.presentation.state.UiChat
import dev.lantt.itindr.core.presentation.mvi.MviIntent

sealed interface MatchMviIntent : MviIntent {
    data class InitUserId(val userId: String) : MatchMviIntent
    data object CreateChat : MatchMviIntent

    data class ChatCreated (val uiChat: UiChat): MatchMviIntent
    data object NetworkError : MatchMviIntent
}