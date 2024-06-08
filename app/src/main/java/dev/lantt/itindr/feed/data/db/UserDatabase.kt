package dev.lantt.itindr.feed.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.lantt.itindr.feed.data.dao.UserDao
import dev.lantt.itindr.feed.data.model.MyProfileEntity
import dev.lantt.itindr.feed.data.model.ProfileEntity
import dev.lantt.itindr.feed.data.model.ProfileTopicCrossRef
import dev.lantt.itindr.feed.data.model.TopicEntity

@Database(
    entities = [
        ProfileEntity::class,
        MyProfileEntity::class,
        TopicEntity::class,
        ProfileTopicCrossRef::class
    ],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}