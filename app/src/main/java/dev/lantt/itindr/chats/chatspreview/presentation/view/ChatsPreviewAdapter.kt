package dev.lantt.itindr.chats.chatspreview.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.lantt.itindr.R
import dev.lantt.itindr.chats.common.domain.entity.ChatPreview
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.ItemChatPreviewBinding

class ChatsPreviewAdapter(
    private val onPreviewClick: (ChatPreview) -> Unit
) : ListAdapter<ChatPreview, ChatsPreviewAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<ChatPreview>() {
            override fun areItemsTheSame(oldItem: ChatPreview, newItem: ChatPreview): Boolean =
                oldItem.chat.id == newItem.chat.id

            override fun areContentsTheSame(oldItem: ChatPreview, newItem: ChatPreview): Boolean =
                oldItem == newItem

        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChatPreviewBinding.bind(view)

        init {
            binding.personAvatarImage.clipToOutline = true

            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onPreviewClick(currentList[adapterPosition])
                }
            }
        }

        fun bind(preview: ChatPreview) = with (binding) {
            nameText.text = preview.chat.title

            if (preview.lastMessage == null) {
                messagePreviewText.text = binding.root.context.getString(R.string.emptyChatPlaceholder)
            } else {
                messagePreviewText.text = preview.lastMessage.text
            }

            if (preview.chat.avatar != null) {
                personAvatarImage.scaleType = ImageView.ScaleType.CENTER_CROP
                personAvatarImage.loadImageWithShimmer(preview.chat.avatar)
            } else {
                personAvatarImage.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_user))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_chat_preview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}