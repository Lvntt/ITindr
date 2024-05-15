package dev.lantt.itindr.auth.presentation.store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lantt.itindr.auth.domain.entity.RegisterBody
import dev.lantt.itindr.auth.domain.entity.ValidationError
import dev.lantt.itindr.auth.domain.usecase.RegisterUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidateEmailUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidatePasswordUseCase
import dev.lantt.itindr.auth.domain.usecase.ValidateRepeatedPasswordUseCase
import dev.lantt.itindr.auth.presentation.mapper.ValidationErrorToStringRes
import dev.lantt.itindr.auth.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.presentation.state.RegisterMviState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private companion object {
        const val TAG = "RegisterViewModel"
    }

    private val stateFlow = MutableStateFlow(RegisterMviState())
    val state = stateFlow.asStateFlow()

    private val effectsFlow = MutableSharedFlow<RegisterMviEffect>()
    val effects = effectsFlow.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.stackTraceToString())
        sendEffect(RegisterMviEffect.ShowError)
    }

    fun dispatch(intent: RegisterMviIntent) {
        when (intent) {
            is RegisterMviIntent.EmailChanged -> {
                stateFlow.update { it.copy(email = intent.email) }
                val validationError = validateEmailUseCase(stateFlow.value.email)
                val emailErrorResId = getErrorStringRes(validationError)
                stateFlow.update { it.copy(emailErrorResId = emailErrorResId) }
            }
            is RegisterMviIntent.PasswordChanged -> {
                stateFlow.update { it.copy(password = intent.password) }
                val validationError = validatePasswordUseCase(stateFlow.value.password)
                val passwordErrorResId = getErrorStringRes(validationError)
                val repeatedPasswordValidationError = validateRepeatedPasswordUseCase(stateFlow.value.password, stateFlow.value.repeatedPassword)
                val repeatedPasswordErrorResId = getErrorStringRes(repeatedPasswordValidationError)
                stateFlow.update {
                    it.copy(
                        passwordErrorResId = passwordErrorResId,
                        repeatedPasswordErrorResId = repeatedPasswordErrorResId
                    )
                }
            }
            is RegisterMviIntent.RepeatedPasswordChanged -> {
                stateFlow.update { it.copy(repeatedPassword = intent.repeatedPassword) }
                val validationError = validateRepeatedPasswordUseCase(stateFlow.value.password, stateFlow.value.repeatedPassword)
                val repeatedPasswordErrorResId = getErrorStringRes(validationError)
                stateFlow.update { it.copy(repeatedPasswordErrorResId = repeatedPasswordErrorResId) }
            }
            RegisterMviIntent.Register -> {
                stateFlow.update { it.copy(registrationState = RegisterMviState.RegistrationState.LOADING) }
                register()
                stateFlow.update { it.copy(registrationState = RegisterMviState.RegistrationState.IDLE) }
            }
            RegisterMviIntent.Back -> {
                sendEffect(RegisterMviEffect.GoToPreviousScreen)
            }
        }
    }

    private fun register() {
        viewModelScope.launch(defaultDispatcher + exceptionHandler) {
            registerUseCase(
                RegisterBody(
                    email = stateFlow.value.email,
                    password = stateFlow.value.password
                )
            )
            sendEffect(RegisterMviEffect.GoToAboutYourselfScreen)
        }
    }

    private fun getErrorStringRes(error: ValidationError?): Int? {
        return ValidationErrorToStringRes.stringResources[error]
    }

    private fun sendEffect(effect: RegisterMviEffect) {
        viewModelScope.launch {
            effectsFlow.emit(effect)
        }
    }

}