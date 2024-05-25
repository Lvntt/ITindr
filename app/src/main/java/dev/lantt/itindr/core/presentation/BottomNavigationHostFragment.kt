package dev.lantt.itindr.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.R
import dev.lantt.itindr.core.presentation.navigation.ITindrNavigator
import dev.lantt.itindr.core.presentation.navigation.Screens.Chats
import dev.lantt.itindr.core.presentation.navigation.Screens.Feed
import dev.lantt.itindr.core.presentation.navigation.Screens.People
import dev.lantt.itindr.core.presentation.navigation.Screens.Profile
import dev.lantt.itindr.databinding.FragmentBottomNavigationHostBinding

class BottomNavigationHostFragment : Fragment() {

    private var _binding: FragmentBottomNavigationHostBinding? = null
    private val binding get() = _binding!!

    private lateinit var router: Router
    private lateinit var navigatorHolder: NavigatorHolder
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cicerone = Cicerone.create()
        router = cicerone.router
        navigator = ITindrNavigator(requireActivity(), R.id.bottomNavigationFragmentHost)
        navigatorHolder = cicerone.getNavigatorHolder()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomNavigationHostBinding.inflate(inflater, container, false)

        val bottomNavigationBar = binding.bottomNavigationBar
        bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navFeed -> {
                    router.navigateTo(Feed())
                    true
                }
                R.id.navPeople -> {
                    router.navigateTo(People())
                    true
                }
                R.id.navChats -> {
                    router.navigateTo(Chats())
                    true
                }
                R.id.navProfile -> {
                    router.navigateTo(Profile())
                    true
                }
                else -> false
            }
        }

        bottomNavigationBar.setOnApplyWindowInsetsListener(null)
        bottomNavigationBar.setPadding(0, 0, 0, 0)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}