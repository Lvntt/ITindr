package dev.lantt.itindr.match.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviState

data class MatchMviState(
    val userId: String = "",
    val isLoading: Boolean = false
) : MviState
