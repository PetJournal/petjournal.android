package com.soujunior.petjournal.ui.registerScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.exceptions.NoConnection
import com.soujunior.domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.launch
import java.io.IOException

class RegisterScreenViewModelImpl(
    private val registerUseCase: RegisterUseCase
) : RegisterScreenViewModel() {

    override val formSuccess = MutableLiveData<String>()
    override val formError = MutableLiveData<String>()

    override fun postForm(form: RegisterModel) {
        viewModelScope.launch {
            var result = registerUseCase.execute(form)
            result.handleResult(::success, ::failed)
        }
    }

    override fun success(resultPostRegister: String) {
        this.formSuccess.value = resultPostRegister
        Log.e("testar", "Success: $resultPostRegister")
    }

    override fun failed() {
        this.formError.value = "lançar um erro aqui"
        Log.e("testar", "Erro ao enviar formulário")
    }
}