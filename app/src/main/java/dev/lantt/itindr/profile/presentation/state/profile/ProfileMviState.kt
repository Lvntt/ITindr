package dev.lantt.itindr.profile.presentation.state.profile

import dev.lantt.itindr.core.presentation.mvi.MviState
import dev.lantt.itindr.feed.presentation.state.UiProfile

data class ProfileMviState(
    val profile: UiProfile = UiProfile(),
    val isLoading: Boolean = false
) : MviState
