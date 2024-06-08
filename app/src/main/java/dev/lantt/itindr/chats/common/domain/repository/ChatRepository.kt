package dev.lantt.itindr.chats.common.domain.repository

import dev.lantt.itindr.chats.common.domain.entity.ChatPreview
import dev.lantt.itindr.chats.common.domain.entity.Message

interface ChatRepository {
    suspend fun getChatPreviews(): List<ChatPreview>
    suspend fun getChatMessages(chatId: String, limit: Int, offset: Int): List<Message>
}