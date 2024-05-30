package dev.lantt.itindr.profile.presentation.mapper

import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.feed.presentation.state.UiTopic
import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.entity.Topic
import dev.lantt.itindr.profile.domain.entity.UpdateProfileBody
import dev.lantt.itindr.profile.presentation.state.SetupMviState

class ProfileMapper {

    fun toUpdateProfileBody(state: SetupMviState) = with(state) {
        UpdateProfileBody(
            avatarUri = avatarUri?.toString(),
            name = name,
            aboutMyself = aboutMyself,
            chosenTopics = topics
                .filter { it.isSelected }
                .map { it.id }
        )
    }

    fun toUiProfile(profile: Profile): UiProfile = with(profile) {
        UiProfile(
            id = userId,
            name = name,
            aboutMyself = aboutMyself ?: "",
            avatarUrl = avatar,
            topics = topics.map { toUiTopic(it) }
        )
    }

    private fun toUiTopic(topic: Topic): UiTopic = with(topic) {
        UiTopic(
            id = id,
            title = title
        )
    }

}