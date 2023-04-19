package com.soujunior.petjournal.ui.registerScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterScreenViewModelImpl(
    private val registerUseCase: RegisterUseCase
) : RegisterScreenViewModel() {

    override val formSuccess = MutableLiveData<String>()
    override val formError = MutableLiveData<String>()

    override fun postForm(form: RegisterModel) {
        viewModelScope.launch {
            val result = registerUseCase.execute(form)
            result.handleResult(::success, ::failed)
        }
    }

    override fun success(resultPostRegister: String) {
        this.formSuccess.value = resultPostRegister
    }

    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            this.formError.value = exception.message
        } else {
            this.formError.value = "lançar um erro aqui fixo"
        }
    }

}