package dev.lantt.itindr.profile.presentation.state.edit

import android.net.Uri
import dev.lantt.itindr.core.presentation.mvi.MviIntent
import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.profile.presentation.model.Topic

sealed interface EditProfileMviIntent : MviIntent {
    data class InitProfile(val uiProfile: UiProfile) : EditProfileMviIntent
    data object AvatarChoiceRequested : EditProfileMviIntent
    data class AvatarPicked(val avatarUri: Uri) : EditProfileMviIntent
    data object AvatarRemoved : EditProfileMviIntent
    data class NameChanged(val name: String) : EditProfileMviIntent
    data class AboutMyselfChanged(val aboutMyself: String) : EditProfileMviIntent
    data class TopicChosen(val topic: Topic) : EditProfileMviIntent
    data object SaveRequested : EditProfileMviIntent

    data object TopicsRequested : EditProfileMviIntent
    data class TopicsRetrieved(val topics: List<Topic>) : EditProfileMviIntent
    data object TopicsError : EditProfileMviIntent
    data object SaveSuccessful : EditProfileMviIntent
    data object SaveFailed : EditProfileMviIntent
}