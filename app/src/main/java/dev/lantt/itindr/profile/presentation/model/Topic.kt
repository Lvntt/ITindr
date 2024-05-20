package dev.lantt.itindr.profile.presentation.model

data class Topic(
    val id: String,
    val title: String,
    val isSelected: Boolean = false
)
