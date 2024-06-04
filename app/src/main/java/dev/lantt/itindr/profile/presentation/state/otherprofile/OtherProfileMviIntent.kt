package dev.lantt.itindr.profile.presentation.state.otherprofile

import dev.lantt.itindr.core.presentation.mvi.MviIntent

sealed interface OtherProfileMviIntent : MviIntent {
    data class UserLiked(val userId: String) : OtherProfileMviIntent
    data class UserDisliked(val userId: String) : OtherProfileMviIntent

    data class MutualLike(val userId: String) : OtherProfileMviIntent
    data class GoBack(val userIdToRemove: String) : OtherProfileMviIntent
    data object NetworkError : OtherProfileMviIntent
}