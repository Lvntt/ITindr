package dev.lantt.itindr.chats.common.domain.entity

data class UserShort(
    val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?
)
