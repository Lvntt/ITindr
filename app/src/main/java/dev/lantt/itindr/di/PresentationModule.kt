package dev.lantt.itindr.di

import dev.lantt.itindr.auth.login.presentation.store.LoginMiddleware
import dev.lantt.itindr.auth.login.presentation.store.LoginReducer
import dev.lantt.itindr.auth.login.presentation.store.LoginViewModel
import dev.lantt.itindr.auth.register.presentation.store.RegisterMiddleware
import dev.lantt.itindr.auth.register.presentation.store.RegisterReducer
import dev.lantt.itindr.auth.register.presentation.store.RegisterViewModel
import dev.lantt.itindr.chats.chatspreview.presentation.store.ChatsPreviewMiddleware
import dev.lantt.itindr.chats.chatspreview.presentation.store.ChatsPreviewReducer
import dev.lantt.itindr.chats.chatspreview.presentation.store.ChatsPreviewViewModel
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.feed.presentation.store.FeedMiddleware
import dev.lantt.itindr.feed.presentation.store.FeedReducer
import dev.lantt.itindr.feed.presentation.store.FeedViewModel
import dev.lantt.itindr.launch.presentation.LaunchViewModel
import dev.lantt.itindr.people.presentation.store.PeopleMiddleware
import dev.lantt.itindr.people.presentation.store.PeopleReducer
import dev.lantt.itindr.people.presentation.store.PeopleViewModel
import dev.lantt.itindr.profile.presentation.mapper.ProfileMapper
import dev.lantt.itindr.profile.presentation.mapper.TopicMapper
import dev.lantt.itindr.profile.presentation.store.otherprofile.OtherProfileMiddleware
import dev.lantt.itindr.profile.presentation.store.otherprofile.OtherProfileReducer
import dev.lantt.itindr.profile.presentation.store.otherprofile.OtherProfileViewModel
import dev.lantt.itindr.profile.presentation.store.setup.SetupMiddleware
import dev.lantt.itindr.profile.presentation.store.setup.SetupReducer
import dev.lantt.itindr.profile.presentation.store.setup.SetupViewModel
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

    factoryOf(::SetupMiddleware)
    factoryOf(::SetupReducer)
    viewModel {
        SetupViewModel(get(), get(), Dispatchers.IO)
    }

    factoryOf(::FeedMiddleware)
    factoryOf(::FeedReducer)
    viewModel {
        FeedViewModel(get(), get(), Dispatchers.IO)
    }

    factoryOf(::PeopleMiddleware)
    factoryOf(::PeopleReducer)
    viewModel {
        PeopleViewModel(get(), get(), Dispatchers.IO)
    }

    factoryOf(::OtherProfileMiddleware)
    factoryOf(::OtherProfileReducer)
    viewModel {
        OtherProfileViewModel(get(), get(), Dispatchers.IO)
    }

    factoryOf(::ChatsPreviewMiddleware)
    factoryOf(::ChatsPreviewReducer)
    viewModel {
        ChatsPreviewViewModel(get(), get(), Dispatchers.IO)
    }
}