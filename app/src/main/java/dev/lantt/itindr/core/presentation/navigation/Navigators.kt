package dev.lantt.itindr.core.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commitNow
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

class ITindrNavigator(
    activity: FragmentActivity,
    containerId: Int
) : AppNavigator(activity, containerId) {
    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        fragmentTransaction.setCustomAnimations(
            androidx.fragment.R.animator.fragment_fade_enter,
            androidx.fragment.R.animator.fragment_fade_exit,
            androidx.fragment.R.animator.fragment_fade_enter,
            androidx.fragment.R.animator.fragment_fade_exit,
        )
    }
}

class BottomNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager
) : AppNavigator(activity, containerId, fragmentManager) {

    override fun applyCommand(command: Command) {
        when (command) {
            is ChangeTabCommand -> changeTab(command)
        }
    }

    private fun changeTab(command: ChangeTabCommand) {
        val currentFragment = fragmentManager.fragments.firstOrNull { !it.isHidden }
        val newFragment = fragmentManager.findFragmentByTag(command.screen.screenKey)

        if (currentFragment == newFragment && currentFragment != null) return

        fragmentManager.commitNow {
            if (newFragment == null) add(
                containerId,
                command.screen.createFragment(fragmentFactory),
                command.screen.screenKey
            )

            currentFragment?.let { fragment -> hide(fragment) }
            newFragment?.let { fragment -> show(fragment) }
        }
    }

}