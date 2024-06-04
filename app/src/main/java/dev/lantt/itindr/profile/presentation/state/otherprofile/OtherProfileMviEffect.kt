package dev.lantt.itindr.profile.presentation.state.otherprofile

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface OtherProfileMviEffect : MviEffect {
    data class Match(val userId: String) : OtherProfileMviEffect
    data class GoBack(val userIdToRemove: String) : OtherProfileMviEffect
    data object ShowError : OtherProfileMviEffect
}