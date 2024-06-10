package dev.lantt.itindr.chats.common.data.api

import dev.lantt.itindr.chats.common.data.model.ChatCreateBody
import dev.lantt.itindr.chats.common.data.model.MessageModel
import dev.lantt.itindr.chats.common.domain.entity.Chat
import dev.lantt.itindr.chats.common.domain.entity.ChatPreview
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApiService {

    @GET(CHAT_URL)
    suspend fun getChatPreviews(): List<ChatPreview>

    @POST(CHAT_URL)
    suspend fun createChat(@Body chatCreateBody: ChatCreateBody): Chat

    @GET(CHAT_MESSAGE_URL)
    suspend fun getChatMessages(
        @Path(CHAT_ID) chatId: String,
        @Query(LIMIT) limit: Int,
        @Query(OFFSET) offset: Int,
    ): List<MessageModel>

    private companion object {
        const val CHAT_URL = "v1/chat"
        const val CHAT_MESSAGE_URL = "v1/chat/{chatId}/message"

        const val CHAT_ID = "chatId"
        const val LIMIT = "limit"
        const val OFFSET = "offset"
    }

}