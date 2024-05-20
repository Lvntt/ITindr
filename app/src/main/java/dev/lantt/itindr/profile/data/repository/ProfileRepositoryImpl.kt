package dev.lantt.itindr.profile.data.repository

import android.content.ContentResolver
import android.net.Uri
import dev.lantt.itindr.profile.data.api.ProfileApiService
import dev.lantt.itindr.profile.data.mapper.ProfileMapper
import dev.lantt.itindr.profile.domain.entity.UpdateProfileBody
import dev.lantt.itindr.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileApiService: ProfileApiService,
    private val profileMapper: ProfileMapper,
    private val contentResolver: ContentResolver
) : ProfileRepository {
    override suspend fun saveProfile(profile: UpdateProfileBody) = withContext(Dispatchers.IO) {
        val avatarUri = Uri.parse(profileMapper.toAvatarUri(profile))

        val fileStream = contentResolver.openInputStream(avatarUri)
        val fileBytes = fileStream?.readBytes() ?: throw IOException("could not read avatar file")
        fileStream.close()

        val multipartBody = MultipartBody.Part.createFormData(
            "avatar",
            avatarUri.lastPathSegment,
            fileBytes.toRequestBody()
        )

        profileApiService.uploadAvatar(multipartBody)
        profileApiService.updateProfile(profileMapper.toRemoteProfile(profile))
    }
}