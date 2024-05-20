package dev.lantt.itindr.profile.data.model

data class UpdateProfileModel(
    val name: String,
    val aboutMyself: String?,
    val topics: List<String>
)
