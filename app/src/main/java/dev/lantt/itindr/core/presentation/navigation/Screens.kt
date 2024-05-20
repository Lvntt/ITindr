package dev.lantt.itindr.core.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import dev.lantt.itindr.profile.presentation.view.ProfileFragment
import dev.lantt.itindr.auth.login.presentation.view.LoginFragment
import dev.lantt.itindr.auth.register.presentation.view.RegisterFragment
import dev.lantt.itindr.start.presentation.StartFragment

object Screens {
    fun Start() = FragmentScreen { StartFragment() }
    fun Register() = FragmentScreen { RegisterFragment() }
    fun Login() = FragmentScreen { LoginFragment() }
    fun AboutYourself() = FragmentScreen { ProfileFragment() }

    // TODO change to FeedFragment
    fun Feed() = FragmentScreen { ProfileFragment() }
}