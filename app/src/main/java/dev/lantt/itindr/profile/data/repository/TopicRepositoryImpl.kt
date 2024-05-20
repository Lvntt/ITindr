package dev.lantt.itindr.profile.data.repository

import dev.lantt.itindr.profile.data.api.TopicApiService
import dev.lantt.itindr.profile.domain.entity.Topic
import dev.lantt.itindr.profile.domain.repository.TopicRepository
import dev.lantt.itindr.profile.presentation.TestTopics

class TopicRepositoryImpl(
    private val topicApiService: TopicApiService
) : TopicRepository {
//    TODO override suspend fun getTopics(): List<Topic> = topicApiService.getTopics()
    override suspend fun getTopics(): List<Topic> = TestTopics.values
}