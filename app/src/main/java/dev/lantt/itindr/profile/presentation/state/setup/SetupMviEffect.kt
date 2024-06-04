package dev.lantt.itindr.profile.presentation.state.setup

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface SetupMviEffect : MviEffect {
    data object ShowAvatarChoice : SetupMviEffect
    data object ShowTopicsError : SetupMviEffect
    data object ShowSaveError : SetupMviEffect
    data object HandleSuccess : SetupMviEffect
}