package dev.lantt.itindr.feed.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MyProfileWithTopics(
    @Embedded val profile: MyProfileEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "topicId",
        associateBy = Junction(ProfileTopicCrossRef::class)
    )
    val topics: List<TopicEntity>
)
