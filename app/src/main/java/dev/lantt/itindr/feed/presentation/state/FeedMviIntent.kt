package dev.lantt.itindr.feed.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviIntent

sealed interface FeedMviIntent : MviIntent {

    data class UserLiked(val id: String) : FeedMviIntent
    data class UserDisliked(val id: String) : FeedMviIntent
    data class UserAvatarClicked(val profile: UiProfile) : FeedMviIntent

    data object FeedRequested : FeedMviIntent
    data class FeedLoadSuccess(val feed: List<UiProfile>) : FeedMviIntent
    data object FeedLoadError : FeedMviIntent
    data object FeedEmpty : FeedMviIntent
    data class MutualLike(val userId: String) : FeedMviIntent
    data object NextUserRequested : FeedMviIntent
    data class ShowNextUser(val updatedFeed: List<UiProfile>) : FeedMviIntent

    data object NetworkError : FeedMviIntent
}