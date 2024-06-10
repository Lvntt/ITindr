package dev.lantt.itindr.profile.data.mapper

import dev.lantt.itindr.feed.data.model.MyProfileEntity
import dev.lantt.itindr.feed.data.model.MyProfileWithTopics
import dev.lantt.itindr.feed.data.model.ProfileTopicCrossRef
import dev.lantt.itindr.feed.data.model.TopicEntity
import dev.lantt.itindr.profile.data.model.UpdateProfileModel
import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.entity.Topic
import dev.lantt.itindr.profile.domain.entity.UpdateProfileBody

class ProfileMapper {

    fun toAvatarUri(updateProfileBody: UpdateProfileBody) = updateProfileBody.avatarUri

    fun toRemoteProfile(updateProfileBody: UpdateProfileBody) = with(updateProfileBody) {
        UpdateProfileModel(
            name = name,
            aboutMyself = aboutMyself,
            topics = chosenTopics
        )
    }

    fun toProfile(profileWithTopics: MyProfileWithTopics) = with(profileWithTopics) {
        Profile(
            userId = profile.userId,
            name = profile.name,
            aboutMyself = profile.aboutMyself,
            avatar = profile.avatar,
            topics = topics.map { toTopic(it) }
        )
    }

    fun toMyProfileEntity(profile: Profile) = with(profile) {
        MyProfileEntity(
            userId = userId,
            name = name,
            aboutMyself = aboutMyself,
            avatar = avatar
        )
    }

    fun toTopic(topicEntity: TopicEntity) = with(topicEntity) {
        Topic(
            id = topicId,
            title = title
        )
    }

    fun toTopicEntity(topic: Topic) = with(topic) {
        TopicEntity(
            topicId = id,
            title = title
        )
    }

    fun toCrossRefs(profile: Profile) = with(profile) {
        profile.topics.map {
            ProfileTopicCrossRef(
                userId = profile.userId,
                topicId = it.id
            )
        }
    }
}