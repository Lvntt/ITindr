package dev.lantt.itindr.feed.data.repository

import dev.lantt.itindr.feed.data.api.UserApiService
import dev.lantt.itindr.feed.domain.entity.LikeResult
import dev.lantt.itindr.feed.domain.repository.UserRepository
import dev.lantt.itindr.profile.domain.entity.Profile

class UserRepositoryImpl(
    private val userApiService: UserApiService
) : UserRepository {
    override suspend fun getUsers(limit: Int, offset: Int): List<Profile> = userApiService.getUsers(limit, offset)
    override suspend fun getFeed(): List<Profile> = userApiService.getFeed()
    override suspend fun like(userId: String): LikeResult = userApiService.like(userId)
    override suspend fun dislike(userId: String) = userApiService.dislike(userId)

}