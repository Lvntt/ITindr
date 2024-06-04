package dev.lantt.itindr.people.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviState
import dev.lantt.itindr.feed.presentation.state.UiProfile

data class PeopleMviState(
    val people: List<UiProfile> = emptyList(),
    val isLoading: Boolean = false,
    val currentPage: Int = 0,
    val isEnded: Boolean = false
) : MviState