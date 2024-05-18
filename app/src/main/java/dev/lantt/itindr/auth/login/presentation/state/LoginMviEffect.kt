package dev.lantt.itindr.auth.login.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface LoginMviEffect : MviEffect {
    data object ShowError : LoginMviEffect
    data object GoToFeedScreen : LoginMviEffect
    data object GoToPreviousScreen : LoginMviEffect
}