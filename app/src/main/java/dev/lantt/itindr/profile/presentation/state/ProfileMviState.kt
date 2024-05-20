package dev.lantt.itindr.profile.presentation.state

import android.net.Uri
import dev.lantt.itindr.core.presentation.mvi.MviState
import dev.lantt.itindr.profile.presentation.model.Topic

data class ProfileMviState(
    val avatarUri: Uri? = null,
    val name: String = "",
    val isNameValid: Boolean = true,
    val aboutMyself: String = "",
    val topics: List<Topic> = emptyList(),
    val areTopicsLoading: Boolean = false,
    val isLoading: Boolean = false
) : MviState {
    val isSaveAllowed = name.isNotBlank() && isNameValid
}
