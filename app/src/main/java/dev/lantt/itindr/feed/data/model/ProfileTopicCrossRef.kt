package dev.lantt.itindr.feed.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "topicId"])
data class ProfileTopicCrossRef(
    val userId: String,
    val topicId: String
)
