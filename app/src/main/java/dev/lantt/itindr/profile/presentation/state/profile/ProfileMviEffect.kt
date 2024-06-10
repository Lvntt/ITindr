package dev.lantt.itindr.profile.presentation.state.profile

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface ProfileMviEffect : MviEffect {
    data object ShowError : ProfileMviEffect
}