package dev.lantt.itindr.profile.presentation.state

import android.net.Uri
import dev.lantt.itindr.core.presentation.mvi.MviIntent
import dev.lantt.itindr.profile.presentation.model.Topic

sealed interface ProfileMviIntent : MviIntent {
    data object AvatarChoiceRequested : ProfileMviIntent
    data class AvatarPicked(val avatarUri: Uri) : ProfileMviIntent
    data object AvatarRemoved : ProfileMviIntent
    data class NameChanged(val name: String) : ProfileMviIntent
    data class TopicChosen(val topic: Topic) : ProfileMviIntent
    data object SaveRequested : ProfileMviIntent

    data class NameValidated(val isValid: Boolean) : ProfileMviIntent
    data object TopicsRequested : ProfileMviIntent
    data class TopicsRetrieved(val topics: List<Topic>) : ProfileMviIntent
    data object TopicsError : ProfileMviIntent
    data object SaveSuccessful : ProfileMviIntent
    data object SaveFailed : ProfileMviIntent
}