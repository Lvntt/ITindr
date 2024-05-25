package dev.lantt.itindr.profile.presentation.store

import android.util.Log
import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.profile.domain.usecase.GetTopicsUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveProfileUseCase
import dev.lantt.itindr.profile.domain.usecase.ValidateNameUseCase
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper
import dev.lantt.itindr.profile.presentation.mapper.TopicMapper
import dev.lantt.itindr.profile.presentation.state.SetupMviIntent
import dev.lantt.itindr.profile.presentation.state.SetupMviState

private const val TAG = "ProfileMiddleware"

class SetupMiddleware(
    private val saveProfileUseCase: SaveProfileUseCase,
    private val getTopicsUseCase: GetTopicsUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val profileMapper: ProfileMapper,
    private val topicMapper: TopicMapper
) : Middleware<SetupMviState, SetupMviIntent> {
    override suspend fun resolve(
        state: SetupMviState,
        intent: SetupMviIntent
    ): SetupMviIntent? {
        return when (intent) {
            SetupMviIntent.SaveRequested -> {
                runCatching {
                    val profileBody = profileMapper.toUpdateProfileBody(state)
                    saveProfileUseCase(profileBody)
                }.fold(
                    onSuccess = {
                        SetupMviIntent.SaveSuccessful
                    },
                    onFailure = {
                        Log.e(TAG, it.stackTraceToString())
                        SetupMviIntent.SaveFailed
                    }
                )
            }
            is SetupMviIntent.NameChanged -> {
                val isValid = validateNameUseCase(intent.name)
                SetupMviIntent.NameValidated(isValid)
            }
            SetupMviIntent.TopicsRequested -> {
                runCatching {
                    getTopicsUseCase()
                }.fold(
                    onSuccess = {
                        val topics = topicMapper.toPresentation(it)
                        SetupMviIntent.TopicsRetrieved(topics)
                    },
                    onFailure = {
                        SetupMviIntent.TopicsError
                    }
                )
            }
            else -> null
        }
    }
}