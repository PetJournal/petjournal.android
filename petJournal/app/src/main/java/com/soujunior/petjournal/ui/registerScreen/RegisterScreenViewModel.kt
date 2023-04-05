package com.soujunior.petjournal.ui.registerScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.RegisterModel

abstract class RegisterScreenViewModel:ViewModel() {
    abstract fun postForm(form: RegisterModel)
    abstract fun failed()
    abstract fun success(resultPostRegister: String)
}