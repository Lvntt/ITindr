package dev.lantt.itindr.feed.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviState
import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.entity.Topic

data class FeedMviState(
    val feed: List<Profile> = emptyList(),
    val id: String = "",
    val name: String = "",
    val aboutMyself: String = "",
    val avatarUrl: String? = null,
    val topics: List<Topic> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
) : MviState
