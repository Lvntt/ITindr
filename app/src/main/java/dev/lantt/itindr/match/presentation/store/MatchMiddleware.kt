package dev.lantt.itindr.match.presentation.store

import dev.lantt.itindr.chats.chat.presentation.state.UiChat
import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.match.domain.CreateChatUseCase
import dev.lantt.itindr.match.presentation.state.MatchMviIntent
import dev.lantt.itindr.match.presentation.state.MatchMviState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MatchMiddleware(
    private val createChatUseCase: CreateChatUseCase,
) : Middleware<MatchMviState, MatchMviIntent> {
    override fun resolve(state: MatchMviState, intent: MatchMviIntent): Flow<MatchMviIntent>? {
        return when (intent) {
            is MatchMviIntent.CreateChat -> flow {
                runCatching {
                    createChatUseCase(state.userId)
                }.fold(
                    onSuccess = {
                        val uiChat = UiChat(
                            id = it.id,
                            title = it.title,
                            avatar = it.avatar
                        )
                        emit(MatchMviIntent.ChatCreated(uiChat))
                    },
                    onFailure = {
                        emit(MatchMviIntent.NetworkError)
                    }
                )
            }
            else -> null
        }
    }
}