package dev.lantt.itindr.feed.domain.repository

import dev.lantt.itindr.feed.domain.entity.LikeResult
import dev.lantt.itindr.profile.domain.entity.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getInitialUsers(): Flow<List<Profile>>
    suspend fun getUsers(limit: Int, offset: Int): List<Profile>
    suspend fun getFeed(): List<Profile>
    suspend fun like(userId: String): LikeResult
    suspend fun dislike(userId: String)
}