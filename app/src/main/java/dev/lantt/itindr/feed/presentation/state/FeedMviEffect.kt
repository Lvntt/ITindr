package dev.lantt.itindr.feed.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface FeedMviEffect : MviEffect {
    data object ShowError : FeedMviEffect
    data class Match(val userId: String) : FeedMviEffect
    data class GoToAboutUser(val profile: UiProfile) : FeedMviEffect
}