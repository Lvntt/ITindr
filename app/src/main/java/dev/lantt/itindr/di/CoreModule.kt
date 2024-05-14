package dev.lantt.itindr.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private fun provideCicerone() = Cicerone.create()
private fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router
private fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()

fun coreModule(): Module = module {
    singleOf(::provideCicerone)
    singleOf(::provideRouter)
    singleOf(::provideNavigatorHolder)
}