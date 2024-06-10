package dev.lantt.itindr.profile.presentation.mapper

import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.feed.presentation.state.UiTopic
import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.entity.Topic
import dev.lantt.itindr.profile.domain.entity.UpdateProfileBody
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviState
import dev.lantt.itindr.profile.presentation.state.setup.SetupMviState

class ProfileMapper {

    fun toUpdateProfileBody(state: SetupMviState) = with(state) {
        UpdateProfileBody(
            avatarUri = avatarUri?.toString(),
            name = name,
            aboutMyself = aboutMyself,
            chosenTopics = topics
                .filter { it.isSelected }
                .map { it.id },
            shouldUpdateAvatar = true
        )
    }

    fun toUpdateProfileBody(state: EditProfileMviState) = with(state) {
        UpdateProfileBody(
            avatarUri = newAvatarUri?.toString(),
            name = newName,
            aboutMyself = newAboutMyself,
            chosenTopics = newTopics
                .filter { it.isSelected }
                .map { it.id },
            shouldUpdateAvatar = wasAvatarChanged
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
            title = title,
            isSelected = true
        )
    }

}