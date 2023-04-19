package com.soujunior.petjournal.ui.loginScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.launch

class LoginScreenViewModelImpl(
    private val loginUseCase: LoginUseCase
) : LoginScreenViewModel() {

    override val loginSuccess = MutableLiveData<String>()
    override val loginError = MutableLiveData<String>()
    override fun postForm(form: LoginModel) {
        Log.e("testar", "${form.email}, ${form.password}")
        viewModelScope.launch {
            val result = loginUseCase.execute(form)
            result.handleResult(::success, ::failed)
        }
    }

    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            this.loginError.value = exception.message
            Log.e("testar", "${exception.message}")

        } else {
            this.loginError.value = "lançar um erro aqui fixo"
        }
    }

    override fun success(resultPostLogin: String) {
        this.loginSuccess.value = resultPostLogin
        Log.e("testar", "$resultPostLogin")
    }

}