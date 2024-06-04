package dev.lantt.itindr.people.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.lantt.itindr.R
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.ItemPersonBinding
import dev.lantt.itindr.feed.presentation.state.UiProfile

class PeopleListAdapter(
    private val onPersonClick: (UiProfile) -> Unit
) : ListAdapter<UiProfile, PeopleListAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<UiProfile>() {
            override fun areItemsTheSame(oldItem: UiProfile, newItem: UiProfile): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiProfile, newItem: UiProfile): Boolean =
                oldItem == newItem

        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPersonBinding.bind(view)

        init {
            binding.personAvatarImage.clipToOutline = true

            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onPersonClick(currentList[adapterPosition])
                }
            }
        }

        fun bind(person: UiProfile) = with(binding) {
            personName.text = person.name

            if (person.avatarUrl != null) {
                personAvatarImage.loadImageWithShimmer(person.avatarUrl)
            } else {
                personAvatarImage.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_user))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_person, parent, false)
        )
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}