package dev.lantt.itindr.core.presentation.navigation

import com.github.terrakok.cicerone.BaseRouter
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

class BottomNavRouter : BaseRouter() {
    fun changeTab(screen: FragmentScreen) {
        executeCommands(ChangeTab(screen))
    }
}

class RootRouter : Router() {
    fun forwardAbove(screen: FragmentScreen) {
        executeCommands(ForwardAbove(screen))
    }
}