package dev.lantt.itindr.feed.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.lantt.itindr.feed.data.model.MyProfileEntity
import dev.lantt.itindr.feed.data.model.MyProfileWithTopics
import dev.lantt.itindr.feed.data.model.ProfileEntity
import dev.lantt.itindr.feed.data.model.ProfileWithTopics
import dev.lantt.itindr.feed.data.model.TopicEntity

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: ProfileEntity)

    @Upsert
    suspend fun upsertTopic(topic: TopicEntity)

    @Transaction
    @Query("SELECT * FROM users LIMIT :limit OFFSET :offset")
    suspend fun getUsersPage(limit: Int, offset: Int): List<ProfileWithTopics>

    @Upsert
    suspend fun upsertMyProfile(myProfile: MyProfileEntity)

    @Query("SELECT * FROM myProfile LIMIT 1")
    suspend fun getMyProfile(): MyProfileWithTopics?

}