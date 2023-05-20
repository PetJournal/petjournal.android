package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.domain.usecase.auth.ForgotPasswordUseCase

import kotlinx.coroutines.launch

class ForgotPasswordScreenViewModelImpl(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ForgotPasswordScreenViewModel() {

    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()

    override fun sendRequestToChangePassword(form: ForgotPasswordModel) {
        viewModelScope.launch {
            val result = forgotPasswordUseCase.execute(form)
            result.handleResult(::success, ::failed)
        }
    }

    override fun failed(error: Throwable?) {
        if (error is Error) {
            this.error.value = error.message

        } else {
            this.error.value = "Erro desconhecido!"
        }
    }

    override fun success(success: String) {
        this.success.value = success
    }
}