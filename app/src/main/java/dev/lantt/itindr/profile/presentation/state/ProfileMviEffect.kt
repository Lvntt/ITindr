package dev.lantt.itindr.profile.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface ProfileMviEffect : MviEffect {
    data object ShowAvatarChoice : ProfileMviEffect
    data object ShowTopicsError : ProfileMviEffect
    data object ShowSaveError : ProfileMviEffect
    data object HandleSuccess : ProfileMviEffect
}