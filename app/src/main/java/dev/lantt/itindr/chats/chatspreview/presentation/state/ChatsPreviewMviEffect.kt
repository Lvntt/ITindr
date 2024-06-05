package dev.lantt.itindr.chats.chatspreview.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface ChatsPreviewMviEffect : MviEffect {
    data object ShowError : ChatsPreviewMviEffect
}