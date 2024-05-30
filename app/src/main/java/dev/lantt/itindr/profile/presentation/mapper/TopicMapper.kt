package dev.lantt.itindr.profile.presentation.mapper

import dev.lantt.itindr.profile.presentation.model.Topic

class TopicMapper {

    fun toPresentation(topics: List<dev.lantt.itindr.profile.domain.entity.Topic>): List<Topic> {
        return topics.map { toPresentation(it) }
    }

    private fun toPresentation(topic: dev.lantt.itindr.profile.domain.entity.Topic): Topic = with(topic) {
        Topic(
            id = id,
            title = title
        )
    }

}