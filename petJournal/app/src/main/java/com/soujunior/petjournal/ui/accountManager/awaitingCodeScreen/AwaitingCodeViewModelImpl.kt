package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.AwaitingCodeUseCase
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

    private val _state = MutableStateFlow(AwaitingCodeFormState())
    override val state: StateFlow<AwaitingCodeFormState> = _state

    private val _buttonIsEnable = MutableStateFlow(false)
    override val buttonIsEnable: StateFlow<Boolean> = _buttonIsEnable

    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()

    override val message: StateFlow<String> get() = setMessage
    private val setMessage = MutableStateFlow("")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    override fun failed(exception: Throwable?) {
        setMessage.value = exception?.message ?: "Erro desconhecido!"
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    override fun success(resultPostAwaitingCode: String) {
        setMessage.value = resultPostAwaitingCode
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun onEvent(event: AwaitingCodeFormEvent) {
        when (event) {
            is AwaitingCodeFormEvent.CodeOTPChanged -> changeCode(event.code)
            is AwaitingCodeFormEvent.EmailChanged -> changeEmail(event.email)
            is AwaitingCodeFormEvent.Submit -> postOtpVerification()
        }
    }

    override fun enableButton(): Boolean {
        val savedState = state.value
        val codeResult = validation.validateCodeOTP(savedState.codeOTP)

        return savedState.codeOTP.isNotBlank() &&
                savedState.codeOTP.isNotEmpty() &&
                savedState.codeOTP.length == 6 &&
                codeResult.errorMessage == null
    }

    private fun changeEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    private fun changeCode(code: String) {
        val codeResult = validation.validateCodeOTP(code)
        _state.value = _state.value.copy(
            codeOTP = code,
            codeOTPError = if (codeResult.success) null else codeResult.errorMessage
        )
        _buttonIsEnable.value = enableButton()
    }

    override fun postOtpVerification() {
        viewModelScope.launch {
            _taskState.value = TaskState.Loading
            val result = awaitingCodeUseCase.execute(
                AwaitingCodeModel(
                    email = state.value.email,
                    verificationToken = state.value.codeOTP
                )
            )
            result.handleResult(::success, ::failed)
            _taskState.value = TaskState.Idle
        }
    }
}