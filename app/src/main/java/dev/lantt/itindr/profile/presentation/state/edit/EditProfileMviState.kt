package dev.lantt.itindr.profile.presentation.state.edit

import android.net.Uri
import dev.lantt.itindr.core.presentation.mvi.MviState
import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.feed.presentation.state.UiTopic

data class EditProfileMviState(
    val unchangedProfile: UiProfile = UiProfile(),
    val newAvatarUri: Uri? = null,
    val wasAvatarChanged: Boolean = false,
    val newName: String = "",
    val newAboutMyself: String = "",
    val newTopics: List<UiTopic> = emptyList(),
    val areTopicsLoading: Boolean = false,
    val isLoading: Boolean = false
) : MviState {

    private val newSelectedTopics = newTopics.filter { it.isSelected }

    val isSaveAllowed =
        newName.isNotBlank()
                && newAboutMyself.isNotBlank()
                && newTopics.isNotEmpty()
                && newSelectedTopics.isNotEmpty()
                && (newName != unchangedProfile.name
                || newAboutMyself != unchangedProfile.aboutMyself
                || newSelectedTopics.toSet() != unchangedProfile.topics.toSet()
                || newAvatarUri != null
                || wasAvatarChanged)
}
