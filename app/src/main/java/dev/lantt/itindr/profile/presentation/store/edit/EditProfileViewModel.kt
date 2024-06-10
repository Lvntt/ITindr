package dev.lantt.itindr.profile.presentation.store.edit

import dev.lantt.itindr.core.presentation.mvi.MviStore
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.edit.EditProfileMviState
import kotlinx.coroutines.CoroutineDispatcher

class EditProfileViewModel(
    middleware: EditProfileMiddleware,
    reducer: EditProfileReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<EditProfileMviState, EditProfileMviIntent, EditProfileMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): EditProfileMviState = EditProfileMviState()

    init {
        dispatch(EditProfileMviIntent.TopicsRequested)
    }
}