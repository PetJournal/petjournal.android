package com.soujunior.petjournal.ui.registerScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.RegisterModel

abstract class RegisterScreenViewModel : ViewModel() {
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
    abstract fun postForm(form: RegisterModel)
    abstract fun failed(exception: Throwable?)
    abstract fun success(resultPostRegister: String)
}