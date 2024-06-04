package dev.lantt.itindr.profile.presentation.state.otherprofile

import dev.lantt.itindr.core.presentation.mvi.MviState

data class OtherProfileMviState(
    val isLoading: Boolean = false
) : MviState