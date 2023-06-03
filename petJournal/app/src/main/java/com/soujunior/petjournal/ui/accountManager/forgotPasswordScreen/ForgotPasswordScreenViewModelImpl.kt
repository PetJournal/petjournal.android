package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.domain.usecase.auth.ForgotPasswordUseCase
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

import kotlinx.coroutines.launch
//TODO: (Gelson) tirar o Screen
class ForgotPasswordScreenViewModelImpl(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
) : ForgotPasswordScreenViewModel() {
    override var state by mutableStateOf(ForgotPassFormState())
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

    //TODO: (Gelson) o onEvent vai ser responsavel por mostrar os eventos seja de click ou outros eventos
    //TODO: (Gelson) adicionar verificação em tempo de digitação para saber se é uma String no formato de email corretamente.
    override fun onEvent(event: ForgotPassFormEvent.EmailChanged) {
        state = state.copy(email = event.email)
    }
    override fun onEvent(event: ForgotPassFormEvent) {
        when (event) {
            is ForgotPassFormEvent.Submit -> {
                Log.e("testa", "evento: ${event}")
                Log.e("testa", "evento: ${state.email}")
                submitData()
            }
            else -> {}
        }
    }


    override fun success(resultPostSubmit: String) {
        this.success.value = resultPostSubmit
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }


    override fun submitData() {
        viewModelScope.launch {
            val result = forgotPasswordUseCase.execute(ForgotPasswordModel(email = state.email))
            result.handleResult(::success, ::failed)
        }
    }
}