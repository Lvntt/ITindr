package dev.lantt.itindr.chats.common.domain.entity

data class Message(
    val id: String,
    val text: String,
    val createdAt: Long,
    val user: UserShort,
    val attachment: String?,
    val isMine: Boolean
)
