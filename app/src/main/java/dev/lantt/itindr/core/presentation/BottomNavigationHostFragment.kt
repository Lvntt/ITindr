package dev.lantt.itindr.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import dev.lantt.itindr.R
import dev.lantt.itindr.core.presentation.navigation.BottomNavRouter
import dev.lantt.itindr.core.presentation.navigation.BottomNavigator
import dev.lantt.itindr.core.presentation.navigation.Screens.Chats
import dev.lantt.itindr.core.presentation.navigation.Screens.Feed
import dev.lantt.itindr.core.presentation.navigation.Screens.People
import dev.lantt.itindr.core.presentation.navigation.Screens.Profile
import dev.lantt.itindr.databinding.FragmentBottomNavigationHostBinding

class BottomNavigationHostFragment : Fragment() {

    private var _binding: FragmentBottomNavigationHostBinding? = null
    private val binding get() = _binding!!

    private lateinit var router: BottomNavRouter
    private lateinit var navigatorHolder: NavigatorHolder
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cicerone = Cicerone.create(BottomNavRouter())
        router = cicerone.router
        navigator = BottomNavigator(requireActivity(), R.id.bottomNavigationFragmentHost, childFragmentManager)
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
                R.id.navFeed -> router.changeTab(Feed())
                R.id.navPeople -> router.changeTab(People())
                R.id.navChats -> router.changeTab(Chats())
                R.id.navProfile -> router.changeTab(Profile())
                else -> return@setOnItemSelectedListener false
            }
            return@setOnItemSelectedListener true
        }

        if (childFragmentManager.fragments.isEmpty()) {
            router.changeTab(Feed())
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