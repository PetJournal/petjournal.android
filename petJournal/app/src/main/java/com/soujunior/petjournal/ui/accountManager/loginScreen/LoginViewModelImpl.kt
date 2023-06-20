package com.soujunior.petjournal.ui.accountManager.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.usecase.auth.LoginUseCase
import com.soujunior.domain.usecase.auth.util.ValidationResult
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModelImpl(
    private val loginUseCase: LoginUseCase,
    private val validation: ValidationRepository
) : LoginViewModel() {
    override var state by mutableStateOf(LoginFormState())
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()
    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()
    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
            this.error.value = exception.message
        } else {
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
            this.error.value = "Erro desconhecido!"
        }
    }
    override fun success(resultPostLogin: String) {
        this.success.value = resultPostLogin
        viewModelScope.launch {
            passwordRemember()
            //TODO: Desenvolver lógica para lembrar senha
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
    override fun passwordRemember() {}
    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
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
                val passwordResult = validation.validateField(state.password)
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
        viewModelScope.launch {
            val result =
                loginUseCase.execute(
                    LoginModel(email = state.email, password = state.password)
                )
            result.handleResult(::success, ::failed)
        }
    }
}