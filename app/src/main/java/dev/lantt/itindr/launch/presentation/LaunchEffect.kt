package dev.lantt.itindr.launch.presentation

sealed interface LaunchEffect {
    data object RedirectToFeedRequired : LaunchEffect
    data object RedirectToSetupRequired : LaunchEffect
    data object RedirectToStartRequired : LaunchEffect
}