package dev.lantt.itindr.di

import android.content.ContentResolver
import dev.lantt.itindr.core.data.repository.AuthRepositoryImpl
import dev.lantt.itindr.core.domain.repository.AuthRepository
import dev.lantt.itindr.profile.data.api.ProfileApiService
import dev.lantt.itindr.profile.data.mapper.ProfileMapper
import dev.lantt.itindr.profile.data.repository.ProfileRepositoryImpl
import dev.lantt.itindr.profile.data.repository.TopicRepositoryImpl
import dev.lantt.itindr.profile.domain.repository.ProfileRepository
import dev.lantt.itindr.profile.domain.repository.TopicRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private fun provideProfileRepository(
    profileApiService: ProfileApiService,
    profileMapper: ProfileMapper,
    contentResolver: ContentResolver
): ProfileRepository =
    ProfileRepositoryImpl(profileApiService, profileMapper, contentResolver)

fun dataModule(): Module = module {
    singleOf(::ProfileMapper)

    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class
    single {
        provideProfileRepository(get(), get(), androidContext().contentResolver)
    }
    singleOf(::TopicRepositoryImpl) bind TopicRepository::class
}