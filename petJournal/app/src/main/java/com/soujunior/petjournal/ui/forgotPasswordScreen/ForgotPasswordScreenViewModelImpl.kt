package com.soujunior.petjournal.ui.forgotPasswordScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.domain.usecase.auth.ForgotPasswordCase

import kotlinx.coroutines.launch

class ForgotPasswordScreenViewModelImpl(
    private val forgotPasswordCase: ForgotPasswordCase
) : ForgotPasswordScreenViewModel() {

    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()

    override fun postForm(form: ForgotPasswordModel) {
        viewModelScope.launch {
            val result = forgotPasswordCase.execute(form)
            result.handleResult(::success, ::failed)
        }
    }

    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            this.error.value = exception.message

        } else {
            this.error.value = "Erro desconhecido!"
        }
    }

    override fun success(resultPostForgotPassword: String) {

        this.success.value = resultPostForgotPassword

    }
}