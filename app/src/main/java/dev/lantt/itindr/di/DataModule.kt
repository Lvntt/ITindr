package dev.lantt.itindr.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import dev.lantt.itindr.chats.common.data.repository.ChatRepositoryImpl
import dev.lantt.itindr.chats.common.domain.repository.ChatRepository
import dev.lantt.itindr.core.data.repository.AuthRepositoryImpl
import dev.lantt.itindr.core.domain.repository.AuthRepository
import dev.lantt.itindr.feed.data.db.UserDatabase
import dev.lantt.itindr.feed.data.mapper.UserMapper
import dev.lantt.itindr.feed.data.repository.UserRepositoryImpl
import dev.lantt.itindr.feed.domain.repository.UserRepository
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

private fun provideUserDatabase(context: Context): UserDatabase =
    Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_db"
    ).build()

private fun provideUserDao(userDatabase: UserDatabase) =
    userDatabase.userDao()

fun dataModule(): Module = module {
    singleOf(::ProfileMapper)
    singleOf(::UserMapper)

    singleOf(::provideUserDatabase)
    singleOf(::provideUserDao)

    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class
    single {
        provideProfileRepository(get(), get(), androidContext().contentResolver)
    }
    singleOf(::TopicRepositoryImpl) bind TopicRepository::class
    singleOf(::UserRepositoryImpl) bind UserRepository::class
    singleOf(::ChatRepositoryImpl) bind ChatRepository::class
}