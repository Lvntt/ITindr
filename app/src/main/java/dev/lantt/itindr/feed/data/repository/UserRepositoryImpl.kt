package dev.lantt.itindr.feed.data.repository

import dev.lantt.itindr.core.constants.Constants.INITIAL_PAGE_SIZE
import dev.lantt.itindr.feed.data.api.UserApiService
import dev.lantt.itindr.feed.data.dao.UserDao
import dev.lantt.itindr.feed.data.mapper.UserMapper
import dev.lantt.itindr.feed.domain.entity.LikeResult
import dev.lantt.itindr.feed.domain.repository.UserRepository
import dev.lantt.itindr.profile.domain.entity.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userApiService: UserApiService,
    private val userDao: UserDao,
    private val userMapper: UserMapper
) : UserRepository {

    override fun getInitialUsers(): Flow<List<Profile>> = flow {
        val localUsers = userDao.getUsersPage(INITIAL_PAGE_SIZE, 0)
        val users = localUsers.map { userMapper.profileToDomain(it) }
        emit(users)

        val remoteUsers = userApiService.getUsers(INITIAL_PAGE_SIZE, 0)
        emit(remoteUsers)

        remoteUsers.forEach { user ->
            val localUser = userMapper.profileToLocal(user)
            userDao.upsertUser(localUser)

            user.topics.forEach { topic ->
                val localTopic = userMapper.topicToLocal(topic)
                userDao.upsertTopic(localTopic)
            }
        }
    }

    override suspend fun getUsers(limit: Int, offset: Int): List<Profile> =
        userApiService.getUsers(limit, offset)

    override suspend fun getFeed(): List<Profile> = userApiService.getFeed()

    override suspend fun like(userId: String): LikeResult = userApiService.like(userId)

    override suspend fun dislike(userId: String) = userApiService.dislike(userId)

}