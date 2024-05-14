package dev.lantt.itindr.aboutyourself.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.lantt.itindr.R
import dev.lantt.itindr.aboutyourself.domain.entity.Interest
import dev.lantt.itindr.databinding.ItemInterestBinding

class InterestsAdapter(
    private val interests: List<Interest>
) : RecyclerView.Adapter<InterestsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInterestBinding.bind(view)

        fun bind(interest: Interest) = with(binding) {
            interestTitleTextView.text = interest.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_interest, parent, false)
        )
    }

    override fun getItemCount(): Int = interests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(interests[position])
    }

}