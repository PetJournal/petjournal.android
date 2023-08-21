package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.data.model.request.ForgotPasswordModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.ForgotPasswordUseCase
import com.soujunior.domain.use_case.auth.util.ValidationResult
import com.soujunior.petjournal.ui.ValidationEvent
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
    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            setMessage.value = exception.message.toString()
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
        } else {
            setMessage.value = "Erro desconhecido!"
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
        }
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
        viewModelScope.launch {
            val result = forgotPasswordUseCase.execute(
                ForgotPasswordModel(email = state.email)
            )
            result.handleResult(::success, ::failed)
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
        return state.email.isNotBlank() &&
                emailResult.errorMessage == null

    }
}