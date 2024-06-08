package dev.lantt.itindr.chats.chat.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.lantt.itindr.R
import dev.lantt.itindr.chats.common.domain.entity.Message
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.ItemIncomingMessageBinding
import dev.lantt.itindr.databinding.ItemOutgoingMessageBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem == newItem

        }
    }

    private val incomingMessageType = R.layout.item_incoming_message
    private val outgoingMessageType = R.layout.item_outgoing_message

    inner class IncomingMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemIncomingMessageBinding.bind(view)

        init {
            binding.userAvatar.clipToOutline = true
            binding.messageImage.clipToOutline = true
        }

        fun bind(message: Message) = with (binding) {
            val messageTime = getMessageTime(message.createdAt)
            val messageDate = getMessageDate(message.createdAt)
            val messageDateTime = binding.root.context.getString(R.string.chatDateTime, messageTime, messageDate)

            val isTextMessage = message.text.isNotBlank()

            textMessageLayout.isVisible = isTextMessage
            imageMessageLayout.isVisible = !isTextMessage

            if (message.user.avatar != null) {
                userAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
                userAvatar.loadImageWithShimmer(message.user.avatar)
            }

            if (isTextMessage) {
                messageText.text = message.text
                textMessageDateTime.text = messageDateTime
            } else if (message.attachment != null) {
                messageImage.loadImageWithShimmer(message.attachment)
                imageMessageDateTime.text = messageDateTime
            }
        }
    }

    inner class OutgoingMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemOutgoingMessageBinding.bind(view)

        init {
            binding.userAvatar.clipToOutline = true
            binding.messageImage.clipToOutline = true
        }

        fun bind(message: Message) = with (binding) {
            val messageTime = getMessageTime(message.createdAt)
            val messageDate = getMessageDate(message.createdAt)
            val messageDateTime = binding.root.context.getString(R.string.chatDateTime, messageTime, messageDate)

            val isTextMessage = message.text.isNotBlank()

            textMessageLayout.isVisible = isTextMessage
            imageMessageLayout.isVisible = !isTextMessage

            if (message.user.avatar != null) {
                userAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
                userAvatar.loadImageWithShimmer(message.user.avatar)
            }

            if (isTextMessage) {
                messageText.text = message.text
                textMessageDateTime.text = messageDateTime
            } else if (message.attachment != null) {
                messageImage.loadImageWithShimmer(message.attachment)
                imageMessageDateTime.text = messageDateTime
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = currentList[position]
        return if (message.isMine)
            outgoingMessageType else
            incomingMessageType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(viewType, parent, false)

        return when (viewType) {
            incomingMessageType -> IncomingMessageViewHolder(view)
            outgoingMessageType -> OutgoingMessageViewHolder(view)
            else -> throw IllegalStateException("invalid view type for chat message")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = currentList[position]

        when (holder) {
            is IncomingMessageViewHolder -> holder.bind(message)
            is OutgoingMessageViewHolder -> holder.bind(message)
            else -> throw IllegalStateException("invalid view holder for chat message")
        }
    }

    private fun getMessageTime(createdAt: Long): String {
        val instant = Instant.ofEpochMilli(createdAt)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return dateTime.format(timeFormatter)
    }

    private fun getMessageDate(createdAt: Long): String {
        val instant = Instant.ofEpochMilli(createdAt)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return dateTime.format(dateFormatter)
    }

}