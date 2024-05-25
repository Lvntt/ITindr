package dev.lantt.itindr.profile.domain.entity

data class Profile(
    val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val topics: List<Topic>
)