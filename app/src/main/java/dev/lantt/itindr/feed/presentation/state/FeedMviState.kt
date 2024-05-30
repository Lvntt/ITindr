package dev.lantt.itindr.feed.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviState

data class FeedMviState(
    val feed: List<UiProfile> = emptyList(),
    val currentProfile: UiProfile = UiProfile(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
) : MviState
