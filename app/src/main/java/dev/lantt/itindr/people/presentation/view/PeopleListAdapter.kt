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
import dev.lantt.itindr.profile.domain.entity.Profile

class PeopleListAdapter : ListAdapter<Profile, PeopleListAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Profile>() {
            override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean =
                oldItem.userId == newItem.userId

            override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean =
                oldItem == newItem

        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPersonBinding.bind(view)

        init {
            binding.personAvatarImage.clipToOutline = true
        }

        fun bind(person: Profile) = with(person) {
            binding.personName.text = name

            if (avatar != null) {
                binding.personAvatarImage.loadImageWithShimmer(avatar)
            } else {
                binding.personAvatarImage.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_user))
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