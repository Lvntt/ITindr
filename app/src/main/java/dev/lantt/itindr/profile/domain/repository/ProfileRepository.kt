package dev.lantt.itindr.profile.domain.repository

import dev.lantt.itindr.profile.domain.entity.Profile
import dev.lantt.itindr.profile.domain.entity.UpdateProfileBody

interface ProfileRepository {
    suspend fun getAndSaveProfile(): Profile
    suspend fun updateProfile(profile: UpdateProfileBody)
}