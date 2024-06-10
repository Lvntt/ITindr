package dev.lantt.itindr.profile.presentation.store.edit

import dev.lantt.itindr.core.presentation.mvi.Reducer
import dev.lantt.itindr.feed.presentation.state.UiTopic
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EditProfileReducer : Reducer<EditProfileMviState, EditProfileMviIntent, EditProfileMviEffect> {

    private val effectsFlow = MutableSharedFlow<EditProfileMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<EditProfileMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(
        state: EditProfileMviState,
        intent: EditProfileMviIntent
    ): EditProfileMviState {
        return when (intent) {
            is EditProfileMviIntent.InitProfile -> {
                state.copy(
                    unchangedProfile = intent.uiProfile,
                    newName = intent.uiProfile.name,
                    newAboutMyself = intent.uiProfile.aboutMyself,
                    newTopics = intent.uiProfile.topics
                )
            }
            is EditProfileMviIntent.AboutMyselfChanged -> state.copy(newAboutMyself = intent.aboutMyself)
            EditProfileMviIntent.AvatarChoiceRequested -> {
                sendEffect(EditProfileMviEffect.ShowAvatarChoice)
                state
            }
            is EditProfileMviIntent.AvatarPicked -> state.copy(newAvatarUri = intent.avatarUri, wasAvatarChanged = true)
            EditProfileMviIntent.AvatarRemoved -> state.copy(newAvatarUri = null, wasAvatarChanged = true)
            is EditProfileMviIntent.NameChanged -> state.copy(newName = intent.name)
            EditProfileMviIntent.SaveFailed -> {
                sendEffect(EditProfileMviEffect.ShowSaveError)
                state.copy(isLoading = false)
            }
            EditProfileMviIntent.SaveRequested -> state.copy(isLoading = true)
            EditProfileMviIntent.SaveSuccessful -> {
                sendEffect(EditProfileMviEffect.HandleSuccess)
                state.copy(isLoading = false)
            }
            is EditProfileMviIntent.TopicChosen -> state.copy(
                newTopics = state.newTopics.map {
                    if (it.id == intent.topic.id)
                        it.copy(isSelected = !it.isSelected)
                    else
                        it
                }
            )
            EditProfileMviIntent.TopicsError -> {
                sendEffect(EditProfileMviEffect.ShowTopicsError)
                state.copy(areTopicsLoading = false)
            }
            EditProfileMviIntent.TopicsRequested -> state.copy(areTopicsLoading = true)
            is EditProfileMviIntent.TopicsRetrieved -> state.copy(
                newTopics = intent.topics.map { remoteTopic ->
                    val isSelected = state.unchangedProfile.topics
                        .find { it.id == remoteTopic.id }
                        ?.isSelected
                        ?: false
                    UiTopic(
                        id = remoteTopic.id,
                        title = remoteTopic.title,
                        isSelected = isSelected
                    )
                    // TODO move to mapper
                },
                areTopicsLoading = false
            )
        }
    }

    private fun sendEffect(effect: EditProfileMviEffect) {
        effectsFlow.tryEmit(effect)
    }
}