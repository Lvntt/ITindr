package dev.lantt.itindr.chats.chat.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface ChatMviEffect : MviEffect {
    data object ShowError : ChatMviEffect
}