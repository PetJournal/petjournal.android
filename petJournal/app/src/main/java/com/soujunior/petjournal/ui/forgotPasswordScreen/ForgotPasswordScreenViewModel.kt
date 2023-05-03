package com.soujunior.petjournal.ui.forgotPasswordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.ForgotPasswordModel

abstract class ForgotPasswordScreenViewModel : ViewModel(){
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>

    abstract fun postForm(form: ForgotPasswordModel)

    abstract fun failed(exception: Throwable?)

    abstract fun success(resultPostForgotPassword: String)
}