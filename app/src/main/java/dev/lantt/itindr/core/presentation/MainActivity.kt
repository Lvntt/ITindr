package dev.lantt.itindr.core.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.terrakok.cicerone.NavigatorHolder
import dev.lantt.itindr.R
import dev.lantt.itindr.core.presentation.navigation.ITindrNavigator
import org.koin.android.ext.android.inject
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = ITindrNavigator(this, R.id.fragmentHost)

    private var downGestureX = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> downGestureX = event.rawX.toInt()
            MotionEvent.ACTION_UP -> {
                val focusedView = currentFocus
                if (focusedView is EditText) {
                    val x = event.rawX.toInt()
                    val y = event.rawY.toInt()

                    if (abs(downGestureX - x) > 5) {
                        return super.dispatchTouchEvent(event)
                    }

                    val outRect = Rect()
                    focusedView.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(x, y)) {
                        focusedView.clearFocus()
                        var touchTargetIsEditText = false

                        focusedView.rootView.touchables.forEach { touchable ->
                            if (touchable is EditText) {
                                val clickedViewRect = Rect()
                                touchable.getGlobalVisibleRect(clickedViewRect)
                                if (clickedViewRect.contains(x, y)) {
                                    touchTargetIsEditText = true
                                    return@forEach
                                }
                            }
                        }

                        if (!touchTargetIsEditText) {
                            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
                        }
                    }
                }
            }
        }

        return super.dispatchTouchEvent(event)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}