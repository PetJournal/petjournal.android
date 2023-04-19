package com.soujunior.petjournal.ui.loginScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.LoginModel

abstract class LoginScreenViewModel : ViewModel() {

    abstract val loginSuccess: LiveData<String>
    abstract val loginError: LiveData<String>

    abstract fun postForm(form: LoginModel)
    abstract fun failed(exception: Throwable?)
    abstract fun success(resultPostLogin: String)

}