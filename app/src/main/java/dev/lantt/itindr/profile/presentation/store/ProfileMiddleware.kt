package dev.lantt.itindr.profile.presentation.store

import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.profile.domain.usecase.GetTopicsUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveProfileUseCase
import dev.lantt.itindr.profile.domain.usecase.ValidateNameUseCase
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper
import dev.lantt.itindr.profile.presentation.mapper.TopicMapper
import dev.lantt.itindr.profile.presentation.state.ProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.ProfileMviState

class ProfileMiddleware(
    private val saveProfileUseCase: SaveProfileUseCase,
    private val getTopicsUseCase: GetTopicsUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val profileMapper: ProfileMapper,
    private val topicMapper: TopicMapper
) : Middleware<ProfileMviState, ProfileMviIntent> {
    override suspend fun resolve(
        state: ProfileMviState,
        intent: ProfileMviIntent
    ): ProfileMviIntent? {
        return when (intent) {
            ProfileMviIntent.SaveRequested -> {
                runCatching {
                    val profileBody = profileMapper.toUpdateProfileBody(state)
                    saveProfileUseCase(profileBody)
                }.fold(
                    onSuccess = {
                        ProfileMviIntent.SaveSuccessful
                    },
                    onFailure = {
                        ProfileMviIntent.SaveFailed
                    }
                )
            }
            is ProfileMviIntent.NameChanged -> {
                val isValid = validateNameUseCase(intent.name)
                ProfileMviIntent.NameValidated(isValid)
            }
            ProfileMviIntent.TopicsRequested -> {
                runCatching {
                    getTopicsUseCase()
                }.fold(
                    onSuccess = {
                        val topics = topicMapper.toPresentation(it)
                        ProfileMviIntent.TopicsRetrieved(topics)
                    },
                    onFailure = {
                        ProfileMviIntent.TopicsError
                    }
                )
            }
            else -> null
        }
    }
}