package dev.lantt.itindr.profile.presentation.store.setup

import dev.lantt.itindr.core.presentation.mvi.Reducer
import dev.lantt.itindr.profile.presentation.state.setup.SetupMviEffect
import dev.lantt.itindr.profile.presentation.state.setup.SetupMviIntent
import dev.lantt.itindr.profile.presentation.state.setup.SetupMviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SetupReducer
    : Reducer<SetupMviState, SetupMviIntent, SetupMviEffect> {

    private val effectsFlow = MutableSharedFlow<SetupMviEffect>(extraBufferCapacity = 1)
    override val effects: SharedFlow<SetupMviEffect> = effectsFlow.asSharedFlow()

    override fun reduce(
        state: SetupMviState,
        intent: SetupMviIntent
    ): SetupMviState {
        return when (intent) {
            is SetupMviIntent.AvatarPicked -> state.copy(avatarUri = intent.avatarUri)
            SetupMviIntent.AvatarRemoved -> state.copy(avatarUri = null)
            is SetupMviIntent.NameChanged -> state.copy(name = intent.name)
            is SetupMviIntent.NameValidated -> state.copy(isNameValid = intent.isValid)
            is SetupMviIntent.AboutMyselfChanged -> state.copy(aboutMyself = intent.aboutMyself)
            SetupMviIntent.SaveRequested -> state.copy(isLoading = true)
            SetupMviIntent.SaveSuccessful -> {
                sendEffect(SetupMviEffect.HandleSuccess)
                state.copy(isLoading = false)
            }

            SetupMviIntent.SaveFailed -> {
                sendEffect(SetupMviEffect.ShowSaveError)
                state.copy(isLoading = false)
            }

            is SetupMviIntent.TopicChosen -> state.copy(
                topics = state.topics.map {
                    if (it.id == intent.topic.id)
                        it.copy(isSelected = !it.isSelected)
                    else
                        it
                }
            )

            SetupMviIntent.TopicsError -> {
                sendEffect(SetupMviEffect.ShowTopicsError)
                state.copy(areTopicsLoading = false)
            }
            SetupMviIntent.TopicsRequested -> state.copy(areTopicsLoading = true)
            is SetupMviIntent.TopicsRetrieved -> state.copy(
                topics = intent.topics,
                areTopicsLoading = false
            )
            SetupMviIntent.AvatarChoiceRequested -> {
                sendEffect(SetupMviEffect.ShowAvatarChoice)
                state
            }
        }
    }

    private fun sendEffect(effect: SetupMviEffect) {
        effectsFlow.tryEmit(effect)
    }

}