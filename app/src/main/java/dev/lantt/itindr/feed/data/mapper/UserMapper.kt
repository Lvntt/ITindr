package dev.lantt.itindr.feed.data.mapper

import dev.lantt.itindr.feed.data.model.ProfileEntity
import dev.lantt.itindr.feed.data.model.ProfileWithTopics
import dev.lantt.itindr.feed.data.model.TopicEntity
import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.entity.Topic

class UserMapper {

    fun profileToDomain(local: ProfileWithTopics): Profile = with (local.profile) {
        Profile(
            userId = userId,
            name = name,
            aboutMyself = aboutMyself,
            avatar = avatar,
            topics = local.topics.map { topicToDomain(it) }
        )
    }

    fun profileToLocal(domain: Profile): ProfileEntity = with (domain) {
        ProfileEntity(
            userId = userId,
            name = name,
            aboutMyself = aboutMyself,
            avatar = avatar
        )
    }

    private fun topicToDomain(local: TopicEntity): Topic =
        Topic(
            id = local.topicId,
            title = local.title
        )

    fun topicToLocal(domain: Topic): TopicEntity =
        TopicEntity(
            topicId = domain.id,
            title = domain.title
        )
}