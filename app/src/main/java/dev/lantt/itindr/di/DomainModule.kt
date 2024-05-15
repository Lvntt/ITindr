package dev.lantt.itindr.di

import dev.lantt.itindr.auth.domain.usecase.RegisterUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidateEmailUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidatePasswordUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidateRepeatedPasswordUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun domainModule(): Module = module {
    factoryOf(::RegisterUseCase)
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateRepeatedPasswordUseCase)
}