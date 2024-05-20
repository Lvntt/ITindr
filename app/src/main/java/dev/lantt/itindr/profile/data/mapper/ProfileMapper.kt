package dev.lantt.itindr.profile.data.mapper

import dev.lantt.itindr.profile.data.model.UpdateProfileModel
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
}