package dev.lantt.itindr.core.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import dev.lantt.itindr.aboutuser.AboutUserFragment
import dev.lantt.itindr.auth.login.presentation.view.LoginFragment
import dev.lantt.itindr.auth.register.presentation.view.RegisterFragment
import dev.lantt.itindr.chats.chat.presentation.state.UiChat
import dev.lantt.itindr.chats.chat.presentation.view.ChatFragment
import dev.lantt.itindr.chats.chatspreview.presentation.view.ChatsPreviewFragment
import dev.lantt.itindr.core.presentation.BottomNavigationHostFragment
import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.feed.presentation.view.FeedFragment
import dev.lantt.itindr.match.presentation.view.MatchFragment
import dev.lantt.itindr.people.presentation.view.PeopleFragment
import dev.lantt.itindr.profile.presentation.view.EditProfileFragment
import dev.lantt.itindr.profile.presentation.view.OtherProfileFragment
import dev.lantt.itindr.profile.presentation.view.ProfileFragment
import dev.lantt.itindr.profile.presentation.view.SetupFragment
import dev.lantt.itindr.start.presentation.StartFragment

object Screens {
    fun Start() = FragmentScreen { StartFragment() }
    fun Register() = FragmentScreen { RegisterFragment() }
    fun Login() = FragmentScreen { LoginFragment() }
    fun Setup() = FragmentScreen { SetupFragment() }
    fun AboutYourself() = FragmentScreen { SetupFragment() }
    fun AboutUser(uiProfile: UiProfile) = FragmentScreen { AboutUserFragment.newInstance(uiProfile) }
    fun MatchScreen(userId: String) = FragmentScreen { MatchFragment.newInstance(userId) }
    fun OtherProfile(uiProfile: UiProfile) = FragmentScreen { OtherProfileFragment.newInstance(uiProfile) }
    fun Chat(uiChat: UiChat) = FragmentScreen { ChatFragment.newInstance(uiChat) }
    fun EditProfile(uiProfile: UiProfile) = FragmentScreen { EditProfileFragment.newInstance(uiProfile) }

    // Bottom Navigation
    fun RootBottomNavigation() = FragmentScreen { BottomNavigationHostFragment() }
    fun Feed() = FragmentScreen { FeedFragment() }
    fun People() = FragmentScreen { PeopleFragment() }
    fun Chats() = FragmentScreen { ChatsPreviewFragment() }
    fun Profile() = FragmentScreen { ProfileFragment() }
}