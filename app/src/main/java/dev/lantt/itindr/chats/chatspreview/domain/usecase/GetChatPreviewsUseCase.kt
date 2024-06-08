package dev.lantt.itindr.chats.chatspreview.domain.usecase

import dev.lantt.itindr.chats.common.domain.repository.ChatRepository

class GetChatPreviewsUseCase(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke() = chatRepository.getChatPreviews()

}