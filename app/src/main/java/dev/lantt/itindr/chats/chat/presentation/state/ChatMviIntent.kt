package dev.lantt.itindr.chats.chat.presentation.state

import dev.lantt.itindr.chats.common.domain.entity.Message
import dev.lantt.itindr.core.presentation.mvi.MviIntent

sealed interface ChatMviIntent : MviIntent {
    data class LoadMoreMessages(val chatId: String) : ChatMviIntent

    data class MessagesPageRetrieved(val messages: List<Message>) : ChatMviIntent
    data object NetworkError : ChatMviIntent
}