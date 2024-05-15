package dev.lantt.itindr.auth.presentation.state

sealed interface RegisterMviEffect {
    data object ShowError : RegisterMviEffect
    data object GoToAboutYourselfScreen : RegisterMviEffect
    data object GoToPreviousScreen : RegisterMviEffect
}