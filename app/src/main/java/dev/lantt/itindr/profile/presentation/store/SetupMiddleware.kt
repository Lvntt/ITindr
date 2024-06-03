package dev.lantt.itindr.profile.presentation.store

import android.util.Log
import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.profile.domain.usecase.GetTopicsUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveIsSetUpUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveProfileUseCase
import dev.lantt.itindr.profile.domain.usecase.ValidateNameUseCase
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper
import dev.lantt.itindr.profile.presentation.mapper.TopicMapper
import dev.lantt.itindr.profile.presentation.state.SetupMviIntent
import dev.lantt.itindr.profile.presentation.state.SetupMviState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "SetupMiddleware"

class SetupMiddleware(
    private val saveProfileUseCase: SaveProfileUseCase,
    private val getTopicsUseCase: GetTopicsUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val saveIsSetUpUseCase: SaveIsSetUpUseCase,
    private val profileMapper: ProfileMapper,
    private val topicMapper: TopicMapper
) : Middleware<SetupMviState, SetupMviIntent> {
    override fun resolve(
        state: SetupMviState,
        intent: SetupMviIntent
    ): Flow<SetupMviIntent>? {
        return when (intent) {
            SetupMviIntent.SaveRequested -> flow {
                runCatching {
                    val profileBody = profileMapper.toUpdateProfileBody(state)
                    saveProfileUseCase(profileBody)
                    saveIsSetUpUseCase()
                }.fold(
                    onSuccess = {
                        emit(SetupMviIntent.SaveSuccessful)
                    },
                    onFailure = {
                        Log.e(TAG, it.stackTraceToString())
                        emit(SetupMviIntent.SaveFailed)
                    }
                )
            }
            is SetupMviIntent.NameChanged -> flow {
                val isValid = validateNameUseCase(intent.name)
                emit(SetupMviIntent.NameValidated(isValid))
            }
            SetupMviIntent.TopicsRequested -> flow {
                runCatching {
                    getTopicsUseCase()
                }.fold(
                    onSuccess = {
                        val topics = topicMapper.toPresentation(it)
                        emit(SetupMviIntent.TopicsRetrieved(topics))
                    },
                    onFailure = {
                        emit(SetupMviIntent.TopicsError)
                    }
                )
            }
            else -> null
        }
    }
}