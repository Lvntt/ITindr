package dev.lantt.itindr.launch.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lantt.itindr.core.domain.exception.UnauthorizedException
import dev.lantt.itindr.launch.domain.usecase.IsUserLoggedInUseCase
import dev.lantt.itindr.launch.domain.usecase.IsUserSetUpUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LaunchViewModel(
    isUserLoggedInUseCase: IsUserLoggedInUseCase,
    isUserSetUpUseCase: IsUserSetUpUseCase,
    defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private companion object {
        const val TAG = "LaunchViewModel"
    }

    private val effectsFlow = MutableSharedFlow<LaunchEffect>(replay = 1)
    val effects = effectsFlow.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.i(TAG, "could not get initial info about user")
        when (throwable) {
            is UnauthorizedException -> effectsFlow.tryEmit(LaunchEffect.RedirectToStartRequired)
        }
    }

    init {
        viewModelScope.launch(defaultDispatcher + exceptionHandler) {
            val isUserLoggedIn = isUserLoggedInUseCase()
            val isUserSetUp = isUserSetUpUseCase()

            val redirectEffect = when {
                isUserLoggedIn && isUserSetUp -> LaunchEffect.RedirectToFeedRequired
                isUserLoggedIn -> LaunchEffect.RedirectToSetupRequired
                else -> LaunchEffect.RedirectToStartRequired
            }

            effectsFlow.emit(redirectEffect)
        }
    }

}