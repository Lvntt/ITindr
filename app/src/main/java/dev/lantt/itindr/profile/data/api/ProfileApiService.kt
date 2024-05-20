package dev.lantt.itindr.profile.data.api

import dev.lantt.itindr.profile.data.model.UpdateProfileModel
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

const val AVATAR_URL = "v1/profile/avatar"
const val PROFILE_URL = "v1/profile"

interface ProfileApiService {

    @Multipart
    @POST(AVATAR_URL)
    suspend fun uploadAvatar(@Part avatar: MultipartBody.Part)

    @PATCH(PROFILE_URL)
    suspend fun updateProfile(@Body profile: UpdateProfileModel)

}