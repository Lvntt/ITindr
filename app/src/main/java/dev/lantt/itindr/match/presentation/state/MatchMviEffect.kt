package dev.lantt.itindr.match.presentation.state

import dev.lantt.itindr.chats.chat.presentation.state.UiChat
import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface MatchMviEffect : MviEffect {
    data class CreateChat(val uiChat: UiChat) : MatchMviEffect
    data object ShowError : MatchMviEffect
}