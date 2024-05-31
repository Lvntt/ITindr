package dev.lantt.itindr.core.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

class ITindrNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager
) : AppNavigator(activity, containerId, fragmentManager) {

    override fun applyCommand(command: Command) {
        when (command) {
            is Forward -> forward(command)
            is ForwardAbove -> forwardAbove(command)
            is Replace -> replace(command)
            is BackTo -> backTo(command)
            is Back -> back()
        }
    }

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

    private fun forwardAbove(command: ForwardAbove) {
        val newFragment = fragmentManager.findFragmentByTag(command.screen.screenKey)

        fragmentManager.commit {
            if (newFragment == null) add(
                containerId,
                command.screen.createFragment(fragmentFactory),
                command.screen.screenKey
            )

            newFragment?.let { fragment -> show(fragment) }

            addToBackStack(command.screen.screenKey)
            localStackCopy.add(command.screen.screenKey)
        }
    }

}

class BottomNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager,
    fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory
) : AppNavigator(activity, containerId, fragmentManager, fragmentFactory) {

    override fun applyCommand(command: Command) {
        when (command) {
            is ChangeTab -> changeTab(command)
        }
    }

    private fun changeTab(command: ChangeTab) {
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

        localStackCopy.add(command.screen.screenKey)
    }

}