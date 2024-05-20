package dev.lantt.itindr.profile.domain.repository

import dev.lantt.itindr.profile.domain.entity.Topic

interface TopicRepository {
    suspend fun getTopics(): List<Topic>
}