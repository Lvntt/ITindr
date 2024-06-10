package dev.lantt.itindr.profile.presentation.state.profile

import dev.lantt.itindr.core.presentation.mvi.MviIntent
import dev.lantt.itindr.feed.presentation.state.UiProfile

sealed interface ProfileMviIntent : MviIntent {
    data object LoadProfile : ProfileMviIntent

    data class ProfileRetrieved(val uiProfile: UiProfile) : ProfileMviIntent
    data object NetworkError : ProfileMviIntent
}