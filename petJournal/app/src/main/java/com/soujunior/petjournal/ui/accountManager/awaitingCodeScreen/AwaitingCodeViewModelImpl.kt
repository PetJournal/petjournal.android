package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.AwaitingCodeUseCase
import com.soujunior.domain.use_case.auth.util.ValidationResult
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AwaitingCodeViewModelImpl(
    private val validation: ValidationRepository,
    private val awaitingCodeUseCase: AwaitingCodeUseCase
) : AwaitingCodeViewModel() {

    override var state by mutableStateOf(AwaitingCodeFormState())
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()
    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    val taskState: StateFlow<TaskState> = _taskState

    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
            this.error.value = exception.message

        } else {
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
            this.error.value = "Erro desconhecido!"
        }
    }
    override fun success(resultPostAwaitingCode: String) {

        this.success.value = resultPostAwaitingCode
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }

    }

    override fun onEvent(event: AwaitingCodeFormEvent) {
        when (event) {
            is AwaitingCodeFormEvent.CodeOTPChanged ->  {
                change(codeOTP = event.code)
                Log.e("test", "${event.code}")
            }
            is AwaitingCodeFormEvent.EmailChanged -> change(email = event.email)
            AwaitingCodeFormEvent.Submit -> postOtpVerification()
        }
    }
    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }

    override fun enableButton(): Boolean {
        val codeResult = validation.validateCodeOTP(state.codeOTP)

        return state.codeOTP.isNotBlank() &&
                state.codeOTP.isNotEmpty() &&
                state.codeOTP.length == 6 &&
                codeResult.errorMessage == null
    }

    private fun change(
        codeOTP: String? = null,
        email: String? = null,
    ) {
        when {
            email != null -> {
                state = state.copy(email = email)
                val emailResult = validation.validateEmail(state.email)
                state = if (hasError(emailResult)) state.copy(emailError = emailResult.errorMessage)
                else state.copy(emailError = null)
            }
            codeOTP != null -> {
                state = state.copy(codeOTP = codeOTP)
                val codeOTPResult = validation.validateCodeOTP(state.codeOTP)
                state = if (hasError(codeOTPResult)) state.copy( codeOTPError = codeOTPResult.errorMessage) // Atribuindo ao emailerror temporariamente
                else state.copy(codeOTPError = null)
                Log.e("test","${state.codeOTPError}")
            }
        }
    }

    override fun clearInput() {
        state = AwaitingCodeFormState()
    }

    override fun postOtpVerification() {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = awaitingCodeUseCase.execute(
                AwaitingCodeModel(
                    email = state.email,
                    verificationToken = state.codeOTP
                )
            )
            result.handleResult(::success, ::failed)
            _taskState.value = TaskState.Idle
            clearInput()
        }
    }
}