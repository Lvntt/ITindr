package dev.lantt.itindr.profile.presentation.store.profile

import dev.lantt.itindr.core.presentation.mvi.Middleware
import dev.lantt.itindr.profile.domain.usecase.GetProfileUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveProfileLocallyUseCase
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.profile.ProfileMviState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileMiddleware(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileLocallyUseCase: SaveProfileLocallyUseCase,
    private val profileMapper: ProfileMapper
) : Middleware<ProfileMviState, ProfileMviIntent> {
    override fun resolve(
        state: ProfileMviState,
        intent: ProfileMviIntent
    ): Flow<ProfileMviIntent>? {
        return when (intent) {
            ProfileMviIntent.LoadProfile -> flow {
                runCatching {
                    saveProfileLocallyUseCase()
                    getProfileUseCase()
                }.fold(
                    onSuccess = {
                        val uiProfile = profileMapper.toUiProfile(it)
                        emit(ProfileMviIntent.ProfileRetrieved(uiProfile))
                    },
                    onFailure = {
                        emit(ProfileMviIntent.NetworkError)
                    }
                )
            }
            else -> null
        }
    }
}