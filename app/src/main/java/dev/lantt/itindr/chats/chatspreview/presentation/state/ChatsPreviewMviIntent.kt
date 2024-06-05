package dev.lantt.itindr.chats.chatspreview.presentation.state

import dev.lantt.itindr.chats.common.domain.entity.ChatPreview
import dev.lantt.itindr.core.presentation.mvi.MviIntent

sealed interface ChatsPreviewMviIntent : MviIntent {
    data object LoadChatsPreview : ChatsPreviewMviIntent

    data class ChatsPreviewLoaded(val previews: List<ChatPreview>) : ChatsPreviewMviIntent
    data object NetworkError : ChatsPreviewMviIntent
}