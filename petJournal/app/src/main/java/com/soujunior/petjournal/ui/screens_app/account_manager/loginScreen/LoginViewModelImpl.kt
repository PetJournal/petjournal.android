package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.GetSavedPasswordUseCase
import com.soujunior.domain.use_case.auth.LoginUseCase
import com.soujunior.domain.use_case.auth.SavePasswordUseCase
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModelImpl(
    private val loginUseCase: LoginUseCase,
    private val validation: ValidationRepository,
    private val savePasswordUseCase: SavePasswordUseCase,
    private val getSavedPasswordUseCase: GetSavedPasswordUseCase
) : LoginViewModel() {

    override var state by mutableStateOf(LoginFormState())
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()

    override val message: StateFlow<String> get() = setMessage
    private val setMessage = MutableStateFlow("")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        viewModelScope.launch {
            val password = getSavedPasswordUseCase()
            if (password != null) {
                state = state.copy(password = password)
            }
        }
    }

    override fun failed(exception: Throwable?) {
        setMessage.value = exception?.message.toString() ?: "Erro desconhecido!"
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    override fun success(resulMessage: String) {
        setMessage.value = resulMessage
        viewModelScope.launch {
            passwordRemember()
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun passwordRemember() {
        if (state.rememberPassword) {
            viewModelScope.launch {
                savePasswordUseCase.execute(state.password)
            }
        }
    }

    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }

    override fun enableButton(): Boolean {
        val emailResult = validation.validateEmail(state.email)
        val passwordResult = validation.validatePassword(password = state.password)
        return state.email.isNotBlank() &&
                state.password.isNotBlank() &&
                emailResult.errorMessage == null &&
                passwordResult.errorMessage == null
    }

    private fun change(
        email: String? = null,
        password: String? = null,
        isRemember: Boolean? = null
    ) {
        when {
            email != null -> {
                state = state.copy(email = email)
                val emailResult = validation.validateEmail(state.email)
                state = if (hasError(emailResult)) state.copy(emailError = emailResult.errorMessage)
                else state.copy(emailError = null)
            }

            password != null -> {
                state = state.copy(password = password)
                val passwordResult = validation.validatePassword(state.password)
                state =
                    if (hasError(passwordResult)) state.copy(passwordError = passwordResult.errorMessage)
                    else state.copy(passwordError = null)
            }

            isRemember != null -> {
                state = state.copy(rememberPassword = isRemember)
            }
        }
    }

    override fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> change(email = event.email)
            is LoginFormEvent.PasswordChanged -> change(password = event.password)
            is LoginFormEvent.RememberPassword -> change(isRemember = event.isRemember)
            is LoginFormEvent.Submit -> submitData()
        }
    }

    override fun submitData() {
        val emailResult = validation.validateEmail(state.email)
        val passwordResult = validation.validateField(state.password)
        val hasError = listOf(emailResult, passwordResult).any { !it.success }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }

        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = loginUseCase.execute(
                LoginModel(
                    email = state.email,
                    password = state.password
                )
            )

            result.handleResult(::success, ::failed)
            _taskState.value = TaskState.Idle
        }
    }
}