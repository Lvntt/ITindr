package dev.lantt.itindr.chats.common.data.mapper

import dev.lantt.itindr.chats.common.data.model.MessageModel
import dev.lantt.itindr.chats.common.domain.entity.Message

class MessageMapper {

    fun toMessage(model: MessageModel, myUserId: String) = with (model) {
        val isMy = model.user.userId == myUserId
        val attachment = if (attachments.isNotEmpty()) attachments[0] else null

        Message(
            id = id,
            text = text,
            createdAt = createdAt,
            user = user,
            attachment = attachment,
            isMine = isMy
        )
    }

}