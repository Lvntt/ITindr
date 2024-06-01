package dev.lantt.itindr.people.presentation.state

import dev.lantt.itindr.core.presentation.mvi.MviEffect

sealed interface PeopleMviEffect : MviEffect {
    data object ShowError : PeopleMviEffect
}