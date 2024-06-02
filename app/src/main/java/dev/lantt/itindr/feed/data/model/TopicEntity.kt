package dev.lantt.itindr.feed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey val topicId: String,
    val title: String
)
