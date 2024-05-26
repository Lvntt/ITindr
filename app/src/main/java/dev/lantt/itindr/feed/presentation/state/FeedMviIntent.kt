package dev.lantt.itindr.feed.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviIntent
import dev.lantt.itindr.profile.domain.entity.Profile

sealed interface FeedMviIntent : MviIntent {

    data class UserLiked(val id: String) : FeedMviIntent
    data class UserDisliked(val id: String) : FeedMviIntent

    data object FeedRequested : FeedMviIntent
    data class FeedLoadSuccess(val feed: List<Profile>) : FeedMviIntent
    data object FeedLoadError : FeedMviIntent
    data object FeedEmpty : FeedMviIntent
    data object MutualLike : FeedMviIntent
    data object NextUserRequested : FeedMviIntent
    data class ShowNextUser(val updatedFeed: List<Profile>) : FeedMviIntent

    data object NetworkError : FeedMviIntent
}