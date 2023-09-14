package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    override val email: StateFlow<String> get() = setEmail
    override val setEmail = MutableStateFlow("")
    override val message: StateFlow<String> get() = setMessage
    private val setMessage = MutableStateFlow("")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    val taskState: StateFlow<TaskState> = _taskState

    override fun failed(exception: Throwable?) {
        setMessage.value = exception?.message.toString() ?: "Erro desconhecido!"
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
            is AwaitingCodeFormEvent.CodeOTPChanged -> {
                change(codeOTP = event.code)
            }

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
    ) {
        when {
            codeOTP != null -> {
                state = state.copy(codeOTP = codeOTP)
                val codeOTPResult = validation.validateCodeOTP(state.codeOTP)
                state =
                    if (hasError(codeOTPResult)) state.copy(codeOTPError = codeOTPResult.errorMessage) // Atribuindo ao emailerror temporariamente
                    else state.copy(codeOTPError = null)
            }
        }
    }

    override fun postOtpVerification() {
        _taskState.value = TaskState.Loading
        if (email.value == null) {
            Log.e(TAG, "Email not passed!")
        } else {
            Log.e(TAG, "Email passed!: ${email.value}")
            viewModelScope.launch {
                val result = awaitingCodeUseCase.execute(
                    AwaitingCodeModel(
                        email = email.value,
                        verificationToken = state.codeOTP
                    )
                )
                result.handleResult(::success, ::failed)
                _taskState.value = TaskState.Idle
            }
        }
    }
}