package com.soujunior.petjournal.ui.registerScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterScreenViewModelImpl(
    private val registerUseCase: RegisterUseCase
) : RegisterScreenViewModel() {

    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()

    override fun postForm(form: RegisterModel) {
        viewModelScope.launch {
            val result = registerUseCase.execute(form)
            result.handleResult(::success, ::failed)
        }
    }

    override fun success(resultPostRegister: String) {
        this.success.value = resultPostRegister
    }

    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            this.error.value = exception.message
        } else {
            this.error.value = "lançar um erro aqui"
        }
    }

}