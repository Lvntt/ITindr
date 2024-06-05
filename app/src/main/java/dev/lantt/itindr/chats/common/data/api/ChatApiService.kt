package dev.lantt.itindr.chats.common.data.api

import dev.lantt.itindr.chats.common.domain.entity.ChatPreview
import retrofit2.http.GET

const val CHAT_URL = "v1/chat"

interface ChatApiService {

    @GET(CHAT_URL)
    suspend fun getChatPreviews(): List<ChatPreview>

}