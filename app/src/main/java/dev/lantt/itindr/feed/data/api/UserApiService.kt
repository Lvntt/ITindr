package dev.lantt.itindr.feed.data.api

import dev.lantt.itindr.feed.domain.entity.LikeResult
import dev.lantt.itindr.profile.domain.entity.Profile
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    @GET(FEED_URL)
    suspend fun getFeed(): List<Profile>

    @POST(LIKE_URL)
    suspend fun like(@Path(USER_ID) userId: String): LikeResult

    @POST(DISLIKE_URL)
    suspend fun dislike(@Path(USER_ID) userId: String)

    private companion object {
        const val FEED_URL = "v1/user/feed"
        const val LIKE_URL = "v1/user/{userId}/like"
        const val DISLIKE_URL = "v1/user/{userId}/dislike"

        const val USER_ID = "userId"
    }
}