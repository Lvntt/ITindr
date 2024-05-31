package dev.lantt.itindr.core.presentation.navigation

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.FragmentScreen

data class ChangeTab(val screen: FragmentScreen) : Command

data class ForwardAbove(val screen: FragmentScreen) : Command