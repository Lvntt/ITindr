package dev.lantt.itindr.chats.common.data.model

import dev.lantt.itindr.chats.common.domain.entity.UserShort

data class MessageModel(
    val id: String,
    val text: String,
    val createdAt: Long,
    val user: UserShort,
    val attachments: List<String>
)