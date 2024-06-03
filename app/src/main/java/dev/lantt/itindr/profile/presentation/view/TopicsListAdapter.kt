package dev.lantt.itindr.profile.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.lantt.itindr.R
import dev.lantt.itindr.databinding.ItemTopicBinding
import dev.lantt.itindr.profile.presentation.model.Topic

class TopicsListAdapter(
    private val onTopicClick: (Topic) -> Unit
) : ListAdapter<Topic, TopicsListAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean =
                oldItem == newItem
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTopicBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onTopicClick(currentList[adapterPosition])
                }
            }
        }

        fun bind(topic: Topic) = with(binding) {
            topicTitleTextView.text = topic.title
            topicTitleTextView.isSelected = topic.isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_topic, parent, false)
        )
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}