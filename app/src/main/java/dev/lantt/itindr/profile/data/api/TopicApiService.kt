package dev.lantt.itindr.profile.data.api

import dev.lantt.itindr.profile.domain.entity.Topic
import retrofit2.http.GET

const val TOPIC_URL = "v1/topic"

interface TopicApiService {

    @GET(TOPIC_URL)
    suspend fun getTopics(): List<Topic>

}