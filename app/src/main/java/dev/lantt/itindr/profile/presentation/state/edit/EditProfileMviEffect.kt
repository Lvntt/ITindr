package dev.lantt.itindr.profile.presentation.state.edit

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface EditProfileMviEffect : MviEffect {
    data object ShowAvatarChoice : EditProfileMviEffect
    data object ShowTopicsError : EditProfileMviEffect
    data object ShowSaveError : EditProfileMviEffect
    data object HandleSuccess : EditProfileMviEffect
}