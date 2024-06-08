package dev.lantt.itindr.chats.common.data.repository

import dev.lantt.itindr.chats.common.data.api.ChatApiService
import dev.lantt.itindr.chats.common.data.mapper.MessageMapper
import dev.lantt.itindr.chats.common.domain.entity.ChatPreview
import dev.lantt.itindr.chats.common.domain.entity.Message
import dev.lantt.itindr.chats.common.domain.repository.ChatRepository
import dev.lantt.itindr.feed.data.dao.UserDao

class ChatRepositoryImpl(
    private val chatApiService: ChatApiService,
    private val userDao: UserDao,
    private val messageMapper: MessageMapper
) : ChatRepository {

    override suspend fun getChatPreviews(): List<ChatPreview> = chatApiService.getChatPreviews()

    override suspend fun getChatMessages(chatId: String, limit: Int, offset: Int): List<Message> {
        val myUserId = userDao.getMyProfile()?.profile?.userId ?: throw IllegalStateException("user cannot have null id")

        val remoteMessages = chatApiService.getChatMessages(chatId, limit, offset)
        return remoteMessages.map { messageMapper.toMessage(it, myUserId) }
    }

}