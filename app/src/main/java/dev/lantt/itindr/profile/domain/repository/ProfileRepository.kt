package dev.lantt.itindr.profile.domain.repository

import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.entity.UpdateProfileBody

interface ProfileRepository {
    suspend fun getProfile(): Profile
    suspend fun saveProfile(profile: UpdateProfileBody)
}