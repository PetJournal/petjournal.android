package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.AwaitingCodeUseCase
import com.soujunior.domain.use_case.auth.ForgotPasswordUseCase
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AwaitingCodeViewModelImpl(
    private val validation: ValidationRepository,
    private val awaitingCodeUseCase: AwaitingCodeUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : AwaitingCodeViewModel() {

    private val _state =
        MutableStateFlow(AwaitingCodeFormState())
    override val state: StateFlow<AwaitingCodeFormState> =
        _state

    private val _buttonIsEnable = MutableStateFlow(false)
    override val buttonIsEnable: StateFlow<Boolean> = _buttonIsEnable

    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()

    private val _message: MutableStateFlow<String> = MutableStateFlow("")
    override val message: StateFlow<String> = _message

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    override fun failed(exception: Throwable?) {
        _message.value = exception?.message ?: "Erro desconhecido!"
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    override fun success(resultPostAwaitingCode: String) {
        _message.value = resultPostAwaitingCode
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun onEvent(event: AwaitingCodeFormEvent) {
        when (event) {
            is AwaitingCodeFormEvent.CodeOTPChanged -> changeCode(event.code)
            is AwaitingCodeFormEvent.EmailChanged -> changeEmail(event.email)
            is AwaitingCodeFormEvent.Submit -> postOtpVerification()
            is AwaitingCodeFormEvent.ResendCode -> resendOtpVerification()
        }
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

    override fun enableButton(): Boolean {
        val savedState = state.value
        val codeResult = validation.validateCodeOTP(savedState.codeOTP)

        return savedState.codeOTP.isNotBlank() &&
                savedState.codeOTP.isNotEmpty() &&
                savedState.codeOTP.length == 6 &&
                codeResult.errorMessage == null
    }

    override fun postOtpVerification() {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
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

    private fun resendOtpVerification() {
        viewModelScope.launch {
            val result = forgotPasswordUseCase.execute(
                ForgotPasswordModel(email = state.value.email)
            )

            if (result.isSuccess) {
                _message.value = "Email reenviado!"
            } else {
                _message.value = "Erro ao reenviar email!"
            }
        }
    }
}
