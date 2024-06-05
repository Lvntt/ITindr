package dev.lantt.itindr.chats.chatspreview.presentation.state

import dev.lantt.itindr.chats.common.domain.entity.ChatPreview
import dev.lantt.itindr.core.presentation.mvi.MviState

data class ChatsPreviewMviState(
    val previews: List<ChatPreview> = emptyList(),
    val isLoading: Boolean = false
) : MviState
