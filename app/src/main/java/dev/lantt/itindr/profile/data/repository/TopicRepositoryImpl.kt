package dev.lantt.itindr.profile.data.repository

import dev.lantt.itindr.profile.data.api.TopicApiService
import dev.lantt.itindr.profile.domain.entity.Topic
import dev.lantt.itindr.profile.domain.repository.TopicRepository

class TopicRepositoryImpl(
    private val topicApiService: TopicApiService
) : TopicRepository {
    override suspend fun getTopics(): List<Topic> = topicApiService.getTopics()
}