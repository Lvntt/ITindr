package dev.lantt.itindr.chats.chat.presentation.store

import dev.lantt.itindr.chats.chat.presentation.state.ChatMviEffect
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviIntent
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviState
import dev.lantt.itindr.core.presentation.mvi.Reducer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ChatReducer : Reducer<ChatMviState, ChatMviIntent, ChatMviEffect> {

    private val effectsFlow = MutableSharedFlow<ChatMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<ChatMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(state: ChatMviState, intent: ChatMviIntent): ChatMviState {
        return when (intent) {
            is ChatMviIntent.LoadMoreMessages -> state.copy(isChatLoading = true)
            is ChatMviIntent.MessagesPageRetrieved -> state.copy(
                messages = state.messages + intent.messages,
                currentPage = state.currentPage + 1,
                isChatLoading = false
            )
            ChatMviIntent.NetworkError -> {
                sendEffect(ChatMviEffect.ShowError)
                state.copy(isChatLoading = false)
            }
        }
    }

    private fun sendEffect(effect: ChatMviEffect) {
        effectsFlow.tryEmit(effect)
    }
}