package com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.ForgotPasswordUseCase
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

import kotlinx.coroutines.launch

class ForgotPasswordViewModelImpl(
    private val validation: ValidationRepository,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ForgotPasswordViewModel() {

    override var state by mutableStateOf(ForgotPasswordFormState())
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()
    override val message: StateFlow<String> get() = setMessage
    private val setMessage = MutableStateFlow("")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    override fun failed(exception: Throwable?) {
        setMessage.value = exception?.message.toString() ?: "Erro desconhecido!"
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    override fun success(resultPostSubmit: String) {
        setMessage.value = resultPostSubmit
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun onEvent(event: ForgotPasswordFormEvent) {
        when (event) {
            is ForgotPasswordFormEvent.EmailChanged ->
                change(email = event.email)
            is ForgotPasswordFormEvent.Submit -> submitData()
        }
    }

    override fun submitData() {
        val emailResult = validation.validateEmail(state.email)
        val hasError = listOf(emailResult).any { !it.success }
        if (hasError) {
            state = state.copy(emailError = emailResult.errorMessage)
            return
        }
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = forgotPasswordUseCase.execute(
                ForgotPasswordModel(email = state.email)
            )
            result.handleResult(::success, ::failed)
            _taskState.value = TaskState.Idle
        }
    }

    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }
    override fun change(
        email: String?
    ) {
        when {
            email != null -> {
                state = state.copy(email = email)
                val emailResult = validation.validateEmail(state.email)
                state = if (hasError(emailResult)) state.copy(emailError = emailResult.errorMessage)
                else state.copy(emailError = null)
            }
        }
    }
    override fun enableButton(): Boolean {
        val emailResult = validation.validateEmail(state.email)
        return state.email.isNotBlank() && emailResult.errorMessage == null
    }
}