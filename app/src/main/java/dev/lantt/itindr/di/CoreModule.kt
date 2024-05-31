package dev.lantt.itindr.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.core.presentation.navigation.RootRouter
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private fun provideRootRouter() = RootRouter()
private fun provideCicerone(router: RootRouter) = Cicerone.create(router)
private fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router
private fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()

fun coreModule(): Module = module {
    singleOf(::provideRootRouter)
    singleOf(::provideCicerone)
    singleOf(::provideRouter)
    singleOf(::provideNavigatorHolder)
}