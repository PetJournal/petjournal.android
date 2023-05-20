package com.soujunior.petjournal.ui.accountManager.loginScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.launch

class LoginScreenViewModelImpl(
    private val loginUseCase: LoginUseCase
) : LoginScreenViewModel() {
    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()
    override fun postForm(form: LoginModel) {
        viewModelScope.launch {
            val result = loginUseCase.execute(form)
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
    override fun success(resultPostLogin: String) {
        this.success.value = resultPostLogin
    }
}