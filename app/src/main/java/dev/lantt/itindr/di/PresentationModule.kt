package dev.lantt.itindr.di

import dev.lantt.itindr.auth.presentation.store.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun presentationModule(): Module = module {
    viewModel {
        RegisterViewModel(get(), get(), get(), get(), Dispatchers.IO)
    }
}