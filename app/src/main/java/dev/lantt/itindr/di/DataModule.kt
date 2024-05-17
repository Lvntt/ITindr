package dev.lantt.itindr.di

import dev.lantt.itindr.core.data.repository.AuthRepositoryImpl
import dev.lantt.itindr.auth.domain.repository.AuthRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun dataModule(): Module = module {
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}