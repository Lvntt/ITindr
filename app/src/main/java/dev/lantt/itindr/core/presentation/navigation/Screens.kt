package dev.lantt.itindr.core.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import dev.lantt.itindr.aboutuser.AboutUserFragment
import dev.lantt.itindr.auth.login.presentation.view.LoginFragment
import dev.lantt.itindr.auth.register.presentation.view.RegisterFragment
import dev.lantt.itindr.chatslist.presentation.view.ChatsListFragment
import dev.lantt.itindr.core.presentation.BottomNavigationHostFragment
import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.feed.presentation.view.FeedFragment
import dev.lantt.itindr.match.MatchFragment
import dev.lantt.itindr.people.presentation.view.PeopleFragment
import dev.lantt.itindr.profile.presentation.view.SetupFragment
import dev.lantt.itindr.start.presentation.StartFragment

object Screens {
    fun Start() = FragmentScreen { StartFragment() }
    fun Register() = FragmentScreen { RegisterFragment() }
    fun Login() = FragmentScreen { LoginFragment() }
    fun AboutYourself() = FragmentScreen { SetupFragment() }
    fun AboutUser(uiProfile: UiProfile) = FragmentScreen { AboutUserFragment.newInstance(uiProfile) }
    fun MatchScreen(userId: String) = FragmentScreen { MatchFragment.newInstance(userId) }

    // Bottom Navigation
    fun RootBottomNavigation() = FragmentScreen { BottomNavigationHostFragment() }
    fun Feed() = FragmentScreen { FeedFragment() }
    fun People() = FragmentScreen { PeopleFragment() }
    fun Chats() = FragmentScreen { ChatsListFragment() }
    fun Setup() = FragmentScreen { SetupFragment() }
}