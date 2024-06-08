package dev.lantt.itindr.chats.chat.presentation.store

import dev.lantt.itindr.chats.chat.domain.usecase.GetChatMessagesUseCase
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviIntent
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviState
import dev.lantt.itindr.core.constants.Constants.MESSAGES_PAGE_LIMIT
import dev.lantt.itindr.core.presentation.mvi.Middleware
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatMiddleware(
    private val getChatMessagesUseCase: GetChatMessagesUseCase
) : Middleware<ChatMviState, ChatMviIntent> {
    override fun resolve(state: ChatMviState, intent: ChatMviIntent): Flow<ChatMviIntent>? {
        return when (intent) {
            is ChatMviIntent.LoadMoreMessages -> flow {
                runCatching {
                    getChatMessagesUseCase(intent.chatId, MESSAGES_PAGE_LIMIT, state.currentPage * MESSAGES_PAGE_LIMIT)
                }.fold(
                    onSuccess = {
                        emit(ChatMviIntent.MessagesPageRetrieved(it))
                    },
                    onFailure = {
                        emit(ChatMviIntent.NetworkError)
                    }
                )
            }
            is ChatMviIntent.MessagesPageRetrieved -> null
            ChatMviIntent.NetworkError -> null
        }
    }
}