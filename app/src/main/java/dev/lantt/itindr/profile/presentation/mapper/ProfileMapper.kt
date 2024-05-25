package dev.lantt.itindr.profile.presentation.mapper

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

}