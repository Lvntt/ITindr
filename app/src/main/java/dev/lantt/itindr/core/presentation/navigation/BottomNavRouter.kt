package dev.lantt.itindr.core.presentation.navigation

import com.github.terrakok.cicerone.BaseRouter
import com.github.terrakok.cicerone.androidx.FragmentScreen

class BottomNavRouter : BaseRouter() {

    fun changeTab(screen: FragmentScreen) {
        executeCommands(ChangeTabCommand(screen))
    }

}