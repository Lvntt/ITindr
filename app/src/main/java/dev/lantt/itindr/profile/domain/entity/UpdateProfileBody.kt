package dev.lantt.itindr.profile.domain.entity

typealias TopicId = String

data class UpdateProfileBody(
    val avatarUri: String?,
    val name: String,
    val aboutMyself: String?,
    val chosenTopics: List<TopicId>,
    val shouldUpdateAvatar: Boolean
)
