package dev.lantt.itindr.core.presentation.mvi

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class MviFragment<State : MviState, Intent : MviIntent, Effect : MviEffect> : Fragment() {

    abstract val store: MviStore<State, Intent, Effect>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        store.state
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        store.effects
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)
    }

    abstract fun render(state: State)

    abstract fun handleEffect(effect: Effect)
}