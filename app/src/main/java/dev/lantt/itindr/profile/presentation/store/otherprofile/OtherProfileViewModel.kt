package dev.lantt.itindr.profile.presentation.store.otherprofile

import dev.lantt.itindr.core.presentation.mvi.MviStore
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviEffect
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviIntent
import dev.lantt.itindr.profile.presentation.state.otherprofile.OtherProfileMviState
import kotlinx.coroutines.CoroutineDispatcher

class OtherProfileViewModel(
    middleware: OtherProfileMiddleware,
    reducer: OtherProfileReducer,
    defaultDispatcher: CoroutineDispatcher
) : MviStore<OtherProfileMviState, OtherProfileMviIntent, OtherProfileMviEffect>(
    middleware, reducer, defaultDispatcher
) {
    override fun initialStateProvider(): OtherProfileMviState = OtherProfileMviState()
}