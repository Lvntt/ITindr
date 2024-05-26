package dev.lantt.itindr.feed.domain.repository

import dev.lantt.itindr.feed.domain.entity.LikeResult
import dev.lantt.itindr.profile.domain.entity.Profile

interface UserRepository {
    suspend fun getFeed(): List<Profile>
    suspend fun like(userId: String): LikeResult
    suspend fun dislike(userId: String)
}