package dev.lantt.itindr.di

import dev.lantt.itindr.auth.register.domain.usecase.RegisterUseCase
import dev.lantt.itindr.auth.common.domain.usecase.ValidateEmailUseCase
import dev.lantt.itindr.auth.common.domain.usecase.ValidatePasswordUseCase
import dev.lantt.itindr.auth.login.domain.usecase.LoginUseCase
import dev.lantt.itindr.auth.register.domain.usecase.ValidateRepeatedPasswordUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun domainModule(): Module = module {
    factoryOf(::RegisterUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateRepeatedPasswordUseCase)
}