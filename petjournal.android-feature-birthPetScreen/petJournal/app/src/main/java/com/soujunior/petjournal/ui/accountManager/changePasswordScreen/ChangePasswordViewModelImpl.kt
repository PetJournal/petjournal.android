package com.soujunior.petjournal.ui.accountManager.changePasswordScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.request.ChangePasswordModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.ChangePasswordUseCase
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModelImpl(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val validation: ValidationRepository
) : ChangePasswordViewModel() {

    override var state by mutableStateOf(ChangePasswordFormState())
    override var validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()

    override val message: StateFlow<String> get() = setMessage
    private val setMessage = MutableStateFlow("")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    override fun success(result: String) {
        setMessage.value = result
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        setMessage.value = exception?.message.toString() ?: "Erro desconhecido!"
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    override fun enableButton(): Boolean {
        val passwordResult = validation.validatePassword(password = state.password)
        val repeatedPasswordResult = validation.validateRepeatedPassword(
            repeatedPassword = state.repeatedPassword,
            password = state.password
        )

        return state.password.isNotBlank() &&
                state.repeatedPassword.isNotBlank() &&
                passwordResult.errorMessage == null &&
                repeatedPasswordResult.errorMessage == null
    }

    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }

    fun change(
        password: String? = null,
        repeatedPassword: String? = null,
        disconnect: Boolean? = null
    ) {
        when {
            password != null -> {
                state = state.copy(password = password)
                val passwordResult = validation.validatePassword(
                    password = state.password
                )
                state =
                    if (hasError(passwordResult)) state.copy(passwordError = passwordResult.errorMessage)
                    else state.copy(passwordError = null)
                change(repeatedPassword = state.repeatedPassword)

            }

            repeatedPassword != null -> {
                state = state.copy(repeatedPassword = repeatedPassword)
                val repeatedPasswordResult = validation.validateRepeatedPassword(
                    repeatedPassword = state.repeatedPassword,
                    password = state.password
                )
                state =
                    if (hasError(repeatedPasswordResult)) state.copy(repeatedPasswordError = repeatedPasswordResult.errorMessage)
                    else state.copy(repeatedPasswordError = null)
            }

            disconnect != null -> {
                state = state.copy(disconnectOtherDevices = disconnect)
            }
        }
    }

    override fun onEvent(event: ChangePasswordFormEvent) {
        when (event) {
            is ChangePasswordFormEvent.PasswordChanged -> {
                change(password = event.password)
            }

            is ChangePasswordFormEvent.ConfirmPasswordChanged -> {
                change(repeatedPassword = event.confirmPassword)
            }

            is ChangePasswordFormEvent.DisconnectOtherDevices -> {
                change(disconnect = event.disconnect)
            }

            is ChangePasswordFormEvent.Submit -> submitNewPassword()
        }
    }

    override fun disconnectOtherDevices() {
        if (state.disconnectOtherDevices) {
            //TODO: criar metodo para desconectar outros dispositivos
        }
    }

    override fun submitNewPassword() {
        disconnectOtherDevices()
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = changePasswordUseCase.execute(
                ChangePasswordModel(
                    password = state.password,
                    passwordConfirmation = state.repeatedPassword
                )
            )
            result.handleResult(::success, ::failed)
            _taskState.value = TaskState.Idle
        }
    }
}