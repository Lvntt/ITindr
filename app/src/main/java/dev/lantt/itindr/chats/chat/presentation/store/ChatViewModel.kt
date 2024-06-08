package dev.lantt.itindr.chats.chat.presentation.store

import dev.lantt.itindr.chats.chat.presentation.state.ChatMviEffect
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviIntent
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviState
import dev.lantt.itindr.core.presentation.mvi.MviStore
import kotlinx.coroutines.CoroutineDispatcher

class ChatViewModel(
    chatId: String,
    middleware: ChatMiddleware,
    reducer: ChatReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<ChatMviState, ChatMviIntent, ChatMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): ChatMviState = ChatMviState()

    init {
        dispatch(ChatMviIntent.LoadMoreMessages(chatId))
    }
}