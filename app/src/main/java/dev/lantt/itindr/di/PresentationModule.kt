package dev.lantt.itindr.di

import dev.lantt.itindr.auth.login.presentation.store.LoginMiddleware
import dev.lantt.itindr.auth.login.presentation.store.LoginReducer
import dev.lantt.itindr.auth.login.presentation.store.LoginViewModel
import dev.lantt.itindr.auth.register.presentation.store.RegisterMiddleware
import dev.lantt.itindr.auth.register.presentation.store.RegisterReducer
import dev.lantt.itindr.auth.register.presentation.store.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun presentationModule(): Module = module {
    factoryOf(::RegisterMiddleware)
    factoryOf(::RegisterReducer)
    viewModel {
        RegisterViewModel(get(), get(), Dispatchers.IO)
    }

    factoryOf(::LoginMiddleware)
    factoryOf(::LoginReducer)
    viewModel {
        LoginViewModel(get(), get(), Dispatchers.IO)
    }
}