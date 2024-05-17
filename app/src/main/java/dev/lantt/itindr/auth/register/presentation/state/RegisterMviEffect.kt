package dev.lantt.itindr.auth.register.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface RegisterMviEffect : MviEffect {
    data object ShowError : RegisterMviEffect
    data object GoToAboutYourselfScreen : RegisterMviEffect
    data object GoToPreviousScreen : RegisterMviEffect
}