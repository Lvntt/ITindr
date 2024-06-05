package dev.lantt.itindr.chats.chatspreview.presentation.store

import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviEffect
import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviIntent
import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviState
import dev.lantt.itindr.core.presentation.mvi.MviStore
import kotlinx.coroutines.CoroutineDispatcher

class ChatsPreviewViewModel(
    middleware: ChatsPreviewMiddleware,
    reducer: ChatsPreviewReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<ChatsPreviewMviState, ChatsPreviewMviIntent, ChatsPreviewMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): ChatsPreviewMviState = ChatsPreviewMviState()

    init {
        dispatch(ChatsPreviewMviIntent.LoadChatsPreview)
    }
}