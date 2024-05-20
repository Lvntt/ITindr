package dev.lantt.itindr.profile.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface ProfileMviEffect : MviEffect {
    data object ShowError : ProfileMviEffect
    data object HandleSuccess : ProfileMviEffect
}