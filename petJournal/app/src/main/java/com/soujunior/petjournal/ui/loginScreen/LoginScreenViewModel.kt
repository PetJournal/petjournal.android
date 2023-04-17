package com.soujunior.petjournal.ui.loginScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.LoginModel

abstract class LoginScreenViewModel: ViewModel() {

    abstract fun postForm(form: LoginModel)

    abstract fun failed()

    abstract fun success(resultPostLogin: String)

}