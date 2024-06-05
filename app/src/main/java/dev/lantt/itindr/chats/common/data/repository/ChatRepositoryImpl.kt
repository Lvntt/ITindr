package dev.lantt.itindr.chats.common.data.repository

import dev.lantt.itindr.chats.common.data.api.ChatApiService
import dev.lantt.itindr.chats.common.domain.entity.ChatPreview
import dev.lantt.itindr.chats.common.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val chatApiService: ChatApiService
) : ChatRepository {

    override suspend fun getChatPreviews(): List<ChatPreview> = chatApiService.getChatPreviews()

}