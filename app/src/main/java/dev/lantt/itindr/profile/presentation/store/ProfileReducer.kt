package dev.lantt.itindr.profile.presentation.store

import android.net.Uri
import dev.lantt.itindr.core.presentation.mvi.Reducer
import dev.lantt.itindr.profile.presentation.state.ProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.ProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.ProfileMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ProfileReducer
    : Reducer<ProfileMviState, ProfileMviIntent, ProfileMviEffect> {

    private val effectsFlow = MutableSharedFlow<ProfileMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<ProfileMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(
        state: ProfileMviState,
        intent: ProfileMviIntent
    ): ProfileMviState {
        return when (intent) {
            is ProfileMviIntent.AvatarPicked -> state.copy(avatarUri = intent.avatarUri)
            ProfileMviIntent.AvatarRemoved -> state.copy(avatarUri = Uri.EMPTY)
            is ProfileMviIntent.NameChanged -> state.copy(name = intent.name)
            is ProfileMviIntent.NameValidated -> state.copy(isNameValid = intent.isValid)
            ProfileMviIntent.SaveRequested -> state.copy(isLoading = true)
            ProfileMviIntent.SaveSuccessful -> {
                sendEffect(ProfileMviEffect.HandleSuccess)
                state.copy(isLoading = false)
            }

            ProfileMviIntent.SaveFailed -> {
                sendEffect(ProfileMviEffect.ShowSaveError)
                state.copy(isLoading = false)
            }

            is ProfileMviIntent.TopicChosen -> state.copy(
                topics = state.topics.map {
                    if (it.id == intent.topic.id)
                        it.copy(isSelected = !it.isSelected)
                    else
                        it
                }
            )

            ProfileMviIntent.TopicsError -> {
                sendEffect(ProfileMviEffect.ShowTopicsError)
                state.copy(areTopicsLoading = false)
            }
            ProfileMviIntent.TopicsRequested -> state.copy(areTopicsLoading = true)
            is ProfileMviIntent.TopicsRetrieved -> state.copy(
                topics = intent.topics,
                areTopicsLoading = false
            )
            ProfileMviIntent.AvatarChoiceRequested -> {
                sendEffect(ProfileMviEffect.ShowAvatarChoice)
                state
            }
        }
    }

    private fun sendEffect(effect: ProfileMviEffect) {
        effectsFlow.tryEmit(effect)
    }

}