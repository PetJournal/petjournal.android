package com.soujunior.petjournal.ui.loginScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.launch

class LoginScreenViewModelImpl(private val loginUseCase: LoginUseCase): LoginScreenViewModel() {

    override fun postForm(form: LoginModel) {

        viewModelScope.launch {
            var result = loginUseCase.execute(form)
            result.handleResult(::success, ::failed)
        }

    }

    override fun failed() {
        // TBD
    }

    override fun success(resultPostLogin: String) {
        // TBD
    }

}