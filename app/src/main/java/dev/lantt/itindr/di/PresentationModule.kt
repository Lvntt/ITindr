package dev.lantt.itindr.di

import dev.lantt.itindr.auth.login.presentation.store.LoginMiddleware
import dev.lantt.itindr.auth.login.presentation.store.LoginReducer
import dev.lantt.itindr.auth.login.presentation.store.LoginViewModel
import dev.lantt.itindr.auth.register.presentation.store.RegisterMiddleware
import dev.lantt.itindr.auth.register.presentation.store.RegisterReducer
import dev.lantt.itindr.auth.register.presentation.store.RegisterViewModel
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.launch.presentation.LaunchViewModel
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper
import dev.lantt.itindr.profile.presentation.mapper.TopicMapper
import dev.lantt.itindr.profile.presentation.store.ProfileMiddleware
import dev.lantt.itindr.profile.presentation.store.ProfileReducer
import dev.lantt.itindr.profile.presentation.store.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun presentationModule(): Module = module {
    singleOf(::ToastManager)

    singleOf(::ProfileMapper)
    singleOf(::TopicMapper)

    viewModel {
        LaunchViewModel(get(), get(), Dispatchers.IO)
    }

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

    factoryOf(::ProfileMiddleware)
    factoryOf(::ProfileReducer)
    viewModel {
        ProfileViewModel(get(), get(), Dispatchers.IO)
    }
}