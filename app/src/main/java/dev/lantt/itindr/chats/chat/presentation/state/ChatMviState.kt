package dev.lantt.itindr.chats.chat.presentation.state

import dev.lantt.itindr.chats.common.domain.entity.Message
import dev.lantt.itindr.core.presentation.mvi.MviState

data class ChatMviState(
    val messages: List<Message> = emptyList(),
    val currentPage: Int = 0,
    val isChatLoading: Boolean = false
) : MviState
