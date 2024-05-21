package dev.lantt.itindr.core.presentation.utils

import android.content.Context
import android.widget.Toast

class ToastManager {

    fun showToast(context: Context?, resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }
}