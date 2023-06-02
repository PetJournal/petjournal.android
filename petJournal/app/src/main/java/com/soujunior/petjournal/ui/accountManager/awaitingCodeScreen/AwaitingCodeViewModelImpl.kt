package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.domain.usecase.auth.AwaitingCodeUseCase
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AwaitingCodeViewModelImpl(
    private val awaitingCodeUseCase: AwaitingCodeUseCase
) : AwaitingCodeViewModel() {

    override var state by mutableStateOf(AwaitingCodeFormState())
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()
    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()

    /*override fun postOtpVerification(code: AwaitingCodeModel) {
        viewModelScope.launch {
            val result = awaitingCodeUseCase.execute(code)
            result.handleResult(::success, ::failed)
        }
    }*/


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
            is AwaitingCodeFormEvent.EmailChanged ->
                state = state.copy(email = event.email)

            is AwaitingCodeFormEvent.CodeOTPChanged ->
                state = state.copy( codeOTP = event.code)
            AwaitingCodeFormEvent.Submit -> postOtpVerification()
        }
    }
    override fun postOtpVerification() {
        viewModelScope.launch {
            val result = awaitingCodeUseCase.execute(AwaitingCodeModel(codeOTP = state.codeOTP, email = state.email))
            result.handleResult(::success, ::failed)
        }
    }

}