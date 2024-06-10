package dev.lantt.itindr.di

import dev.lantt.itindr.auth.common.domain.usecase.ValidateEmailUseCase
import dev.lantt.itindr.auth.common.domain.usecase.ValidatePasswordUseCase
import dev.lantt.itindr.auth.login.domain.usecase.LoginUseCase
import dev.lantt.itindr.auth.register.domain.usecase.RegisterUseCase
import dev.lantt.itindr.auth.register.domain.usecase.ValidateRepeatedPasswordUseCase
import dev.lantt.itindr.chats.chat.domain.usecase.GetChatMessagesUseCase
import dev.lantt.itindr.chats.chatspreview.domain.usecase.GetChatPreviewsUseCase
import dev.lantt.itindr.feed.domain.usecase.DislikeUserUseCase
import dev.lantt.itindr.feed.domain.usecase.GetFeedUseCase
import dev.lantt.itindr.feed.domain.usecase.LikeUserUseCase
import dev.lantt.itindr.launch.domain.usecase.IsUserLoggedInUseCase
import dev.lantt.itindr.launch.domain.usecase.IsUserSetUpUseCase
import dev.lantt.itindr.match.domain.CreateChatUseCase
import dev.lantt.itindr.people.presentation.domain.usecase.GetInitialUsersUseCase
import dev.lantt.itindr.people.presentation.domain.usecase.GetUserListUseCase
import dev.lantt.itindr.profile.domain.usecase.GetProfileUseCase
import dev.lantt.itindr.profile.domain.usecase.GetTopicsUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveIsSetUpUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveProfileLocallyUseCase
import dev.lantt.itindr.profile.domain.usecase.SaveProfileUseCase
import dev.lantt.itindr.profile.domain.usecase.ValidateNameUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun domainModule(): Module = module {
    factoryOf(::RegisterUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateRepeatedPasswordUseCase)
    factoryOf(::GetTopicsUseCase)
    factoryOf(::GetProfileUseCase)
    factoryOf(::SaveProfileUseCase)
    factoryOf(::ValidateNameUseCase)
    factoryOf(::IsUserSetUpUseCase)
    factoryOf(::SaveIsSetUpUseCase)
    factoryOf(::IsUserLoggedInUseCase)
    factoryOf(::GetFeedUseCase)
    factoryOf(::LikeUserUseCase)
    factoryOf(::DislikeUserUseCase)
    factoryOf(::GetUserListUseCase)
    factoryOf(::GetInitialUsersUseCase)
    factoryOf(::GetChatPreviewsUseCase)
    factoryOf(::GetChatMessagesUseCase)
    factoryOf(::SaveProfileLocallyUseCase)
    factoryOf(::CreateChatUseCase)
}