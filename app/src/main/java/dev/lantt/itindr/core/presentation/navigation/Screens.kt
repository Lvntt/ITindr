package dev.lantt.itindr.core.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import dev.lantt.itindr.launch.presentation.LaunchFragment
import dev.lantt.itindr.login.presentation.LoginFragment
import dev.lantt.itindr.register.presentation.RegisterFragment
import dev.lantt.itindr.start.presentation.StartFragment

object Screens {
    fun Launch() = FragmentScreen { LaunchFragment() }
    fun Start() = FragmentScreen { StartFragment() }
    fun Register() = FragmentScreen { RegisterFragment() }
    fun Login() = FragmentScreen { LoginFragment() }
}