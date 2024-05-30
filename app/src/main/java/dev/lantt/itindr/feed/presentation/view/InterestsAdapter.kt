package dev.lantt.itindr.feed.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.lantt.itindr.R
import dev.lantt.itindr.databinding.ItemTopicBinding
import dev.lantt.itindr.feed.presentation.state.UiTopic

class InterestsAdapter(
    private val interests: List<UiTopic>
) : RecyclerView.Adapter<InterestsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTopicBinding.bind(view)

        fun bind(topic: UiTopic) = with (binding) {
            topicTitleTextView.text = topic.title
            topicTitleTextView.isSelected = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_topic, parent, false)
        )
    }

    override fun getItemCount(): Int = interests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(interests[position])
    }

}