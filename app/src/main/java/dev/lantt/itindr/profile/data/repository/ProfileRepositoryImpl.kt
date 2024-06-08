package dev.lantt.itindr.profile.data.repository

import android.content.ContentResolver
import android.net.Uri
import dev.lantt.itindr.core.constants.Constants.UNAUTHORIZED_CODE
import dev.lantt.itindr.core.domain.exception.UnauthorizedException
import dev.lantt.itindr.feed.data.dao.UserDao
import dev.lantt.itindr.profile.data.api.ProfileApiService
import dev.lantt.itindr.profile.data.mapper.ProfileMapper
import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.entity.UpdateProfileBody
import dev.lantt.itindr.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileApiService: ProfileApiService,
    private val profileMapper: ProfileMapper,
    private val userDao: UserDao,
    private val contentResolver: ContentResolver
) : ProfileRepository {
    override suspend fun getAndSaveProfile(): Profile {
        val localProfile = userDao.getMyProfile()
        if (localProfile != null) {
            return profileMapper.toProfile(localProfile)
        }

        try {
            val profile = profileApiService.getProfile()
            userDao.upsertMyProfile(profileMapper.toMyProfileEntity(profile))
            return profile
        } catch (e: HttpException) {
            if (e.code() == UNAUTHORIZED_CODE) {
                throw UnauthorizedException()
            } else {
                throw e
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateProfile(profile: UpdateProfileBody) = withContext(Dispatchers.IO) {
        profileApiService.updateProfile(profileMapper.toRemoteProfile(profile))

        if (profile.avatarUri == null) {
            return@withContext
        }

        val avatarUri = Uri.parse(profileMapper.toAvatarUri(profile))

        val fileStream = contentResolver.openInputStream(avatarUri)
        val fileBytes = fileStream?.readBytes() ?: throw IOException("could not read avatar file")
        fileStream.close()
        val multipartBody = MultipartBody.Part.createFormData(
            "avatar",
            getAvatarFileName(avatarUri),
            fileBytes.toRequestBody()
        )
        profileApiService.uploadAvatar(multipartBody)
    }

    private fun getAvatarFileName(avatarUri: Uri): String {
        val fileType = contentResolver
            .getType(avatarUri)
            ?.split("/")
            ?.last()
            ?: throw IllegalArgumentException("could not get avatar filetype")

        return "${avatarUri.lastPathSegment}.$fileType"
    }
}