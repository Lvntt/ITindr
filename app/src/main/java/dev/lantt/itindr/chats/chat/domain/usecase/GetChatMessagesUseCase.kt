package dev.lantt.itindr.chats.chat.domain.usecase

import dev.lantt.itindr.chats.common.domain.repository.ChatRepository

class GetChatMessagesUseCase(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(chatId: String, limit: Int, offset: Int) =
        chatRepository.getChatMessages(chatId, limit, offset)

}