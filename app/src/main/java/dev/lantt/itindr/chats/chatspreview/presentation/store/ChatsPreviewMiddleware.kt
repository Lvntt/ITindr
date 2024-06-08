package dev.lantt.itindr.chats.chatspreview.presentation.store

import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviIntent
import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviState
import dev.lantt.itindr.chats.chatspreview.domain.usecase.GetChatPreviewsUseCase
import dev.lantt.itindr.core.presentation.mvi.Middleware
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatsPreviewMiddleware(
    private val getChatPreviewsUseCase: GetChatPreviewsUseCase
) : Middleware<ChatsPreviewMviState, ChatsPreviewMviIntent> {
    override fun resolve(
        state: ChatsPreviewMviState,
        intent: ChatsPreviewMviIntent
    ): Flow<ChatsPreviewMviIntent>? {
        return when (intent) {
            ChatsPreviewMviIntent.LoadChatsPreview -> flow {
                runCatching {
                    getChatPreviewsUseCase()
                }.fold(
                    onSuccess = {
                        emit(ChatsPreviewMviIntent.ChatsPreviewLoaded(it))
                    },
                    onFailure = {
                        emit(ChatsPreviewMviIntent.NetworkError)
                    }
                )
            }
            else -> null
        }
    }
}