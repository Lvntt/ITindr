package dev.lantt.itindr.feed.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProfileWithTopics(
    @Embedded val profile: ProfileEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "topicId",
        associateBy = Junction(ProfileTopicCrossRef::class)
    )
    val topics: List<TopicEntity>
)
