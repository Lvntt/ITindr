package dev.lantt.itindr.launch.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.core.presentation.navigation.Screens.Profile
import dev.lantt.itindr.core.presentation.navigation.Screens.RootBottomNavigation
import dev.lantt.itindr.core.presentation.navigation.Screens.Start
import dev.lantt.itindr.databinding.FragmentLaunchBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class LaunchFragment : Fragment() {

    private var _binding: FragmentLaunchBinding? = null
    private val binding get() = _binding!!

    private val store: LaunchViewModel by inject()

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        store.effects
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)
    }

    private fun handleEffect(effect: LaunchEffect) {
        val destinationScreen = when (effect) {
            LaunchEffect.RedirectToFeedRequired -> RootBottomNavigation()
            LaunchEffect.RedirectToSetupRequired -> Profile()
            LaunchEffect.RedirectToStartRequired -> Start()
        }
        router.replaceScreen(destinationScreen)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}