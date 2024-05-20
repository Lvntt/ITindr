package dev.lantt.itindr.profile.domain.usecase

import dev.lantt.itindr.profile.domain.entity.Topic
import dev.lantt.itindr.profile.domain.repository.TopicRepository

class GetTopicsUseCase(
    private val topicRepository: TopicRepository
) {

    suspend operator fun invoke(): List<Topic> = topicRepository.getTopics()

}