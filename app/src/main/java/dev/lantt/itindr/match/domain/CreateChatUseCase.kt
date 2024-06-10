package dev.lantt.itindr.match.domain

import dev.lantt.itindr.chats.common.domain.repository.ChatRepository

class CreateChatUseCase(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(userId: String) = chatRepository.createChat(userId)

}