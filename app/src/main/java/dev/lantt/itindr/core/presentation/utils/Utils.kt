package dev.lantt.itindr.core.presentation.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object Utils {

    private val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(750L)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    private val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    fun ImageView.loadImageWithShimmer(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(shimmerDrawable)
            .into(this)
    }

}