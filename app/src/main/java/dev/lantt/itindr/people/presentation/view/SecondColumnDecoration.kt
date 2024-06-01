package dev.lantt.itindr.people.presentation.view

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import dev.lantt.itindr.R

class SecondColumnSpacingDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val spacing = context.resources.getDimensionPixelSize(R.dimen.padding_xlarge)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        view.updateLayoutParams<MarginLayoutParams> {
            topMargin = if (position == 1) spacing else 0
        }
    }

}