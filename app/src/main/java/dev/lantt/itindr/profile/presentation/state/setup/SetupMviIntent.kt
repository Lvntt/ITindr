package dev.lantt.itindr.profile.presentation.state.setup

import android.net.Uri
import dev.lantt.itindr.core.presentation.mvi.MviIntent
import dev.lantt.itindr.profile.presentation.model.Topic

sealed interface SetupMviIntent : MviIntent {
    data object AvatarChoiceRequested : SetupMviIntent
    data class AvatarPicked(val avatarUri: Uri) : SetupMviIntent
    data object AvatarRemoved : SetupMviIntent
    data class NameChanged(val name: String) : SetupMviIntent
    data class AboutMyselfChanged(val aboutMyself: String) : SetupMviIntent
    data class TopicChosen(val topic: Topic) : SetupMviIntent
    data object SaveRequested : SetupMviIntent

    data class NameValidated(val isValid: Boolean) : SetupMviIntent
    data object TopicsRequested : SetupMviIntent
    data class TopicsRetrieved(val topics: List<Topic>) : SetupMviIntent
    data object TopicsError : SetupMviIntent
    data object SaveSuccessful : SetupMviIntent
    data object SaveFailed : SetupMviIntent
}