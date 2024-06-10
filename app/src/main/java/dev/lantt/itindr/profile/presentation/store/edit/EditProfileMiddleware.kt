package dev.lantt.itindr.profile.presentation.store.edit

import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.profile.domain.usecase.GetTopicsUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveProfileUseCase
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper
import dev.lantt.itindr.profile.presentation.mapper.TopicMapper
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EditProfileMiddleware(
    private val saveProfileUseCase: SaveProfileUseCase,
    private val getTopicsUseCase: GetTopicsUseCase,
    private val profileMapper: ProfileMapper,
    private val topicMapper: TopicMapper
) : Middleware<EditProfileMviState, EditProfileMviIntent> {
    override fun resolve(
        state: EditProfileMviState,
        intent: EditProfileMviIntent
    ): Flow<EditProfileMviIntent>? {
        return when (intent) {
            EditProfileMviIntent.SaveRequested -> flow {
                runCatching {
                    val profileBody = profileMapper.toUpdateProfileBody(state)
                    saveProfileUseCase(profileBody)
                }.fold(
                    onSuccess = {
                        emit(EditProfileMviIntent.SaveSuccessful)
                    },
                    onFailure = {
                        emit(EditProfileMviIntent.SaveFailed)
                    }
                )
            }
            EditProfileMviIntent.TopicsRequested -> flow {
                runCatching {
                    getTopicsUseCase()
                }.fold(
                    onSuccess = {
                        val topics = topicMapper.toPresentation(it)
                        emit(EditProfileMviIntent.TopicsRetrieved(topics))
                    },
                    onFailure = {
                        emit(EditProfileMviIntent.TopicsError)
                    }
                )
            }
            else -> null
        }
    }
}