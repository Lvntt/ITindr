package dev.lantt.itindr.core.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
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