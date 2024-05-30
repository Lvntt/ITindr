package dev.lantt.itindr.feed.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface FeedMviEffect : MviEffect {
    data object ShowError : FeedMviEffect
    data object Match : FeedMviEffect
    data class GoToAboutUser(val profile: UiProfile) : FeedMviEffect
}