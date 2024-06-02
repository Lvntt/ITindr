package dev.lantt.itindr.people.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviState
import dev.lantt.itindr.profile.domain.entity.Profile

data class PeopleMviState(
    val people: List<Profile> = emptyList(),
    val isLoading: Boolean = false,
    val currentPage: Int = 0,
    val isEnded: Boolean = false
) : MviState