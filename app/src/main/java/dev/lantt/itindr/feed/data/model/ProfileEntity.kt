package dev.lantt.itindr.feed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class ProfileEntity(
    @PrimaryKey val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?
)
