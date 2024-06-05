package dev.lantt.itindr.chats.common.domain.repository

import dev.lantt.itindr.chats.common.domain.entity.ChatPreview

interface ChatRepository {
    suspend fun getChatPreviews(): List<ChatPreview>
}