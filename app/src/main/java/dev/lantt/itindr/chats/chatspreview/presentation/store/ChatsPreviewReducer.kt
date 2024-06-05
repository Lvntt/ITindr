package dev.lantt.itindr.chats.chatspreview.presentation.store

import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviEffect
import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviIntent
import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviState
import dev.lantt.itindr.core.presentation.mvi.Reducer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ChatsPreviewReducer : Reducer<ChatsPreviewMviState, ChatsPreviewMviIntent, ChatsPreviewMviEffect> {

    private val effectsFlow = MutableSharedFlow<ChatsPreviewMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<ChatsPreviewMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(
        state: ChatsPreviewMviState,
        intent: ChatsPreviewMviIntent
    ): ChatsPreviewMviState {
        return when (intent) {
            ChatsPreviewMviIntent.LoadChatsPreview -> state.copy(isLoading = true)
            is ChatsPreviewMviIntent.ChatsPreviewLoaded -> state.copy(previews = intent.previews, isLoading = false)
            ChatsPreviewMviIntent.NetworkError -> {
                sendEffect(ChatsPreviewMviEffect.ShowError)
                state.copy(isLoading = false)
            }
        }
    }

    private fun sendEffect(effect: ChatsPreviewMviEffect) {
        effectsFlow.tryEmit(effect)
    }
}