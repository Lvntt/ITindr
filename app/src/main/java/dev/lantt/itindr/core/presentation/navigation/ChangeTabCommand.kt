package dev.lantt.itindr.core.presentation.navigation

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.FragmentScreen

data class ChangeTabCommand(val screen: FragmentScreen) : Command
