package com.soujunior.petjournal.ui.forgotPasswordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.ForgotPasswordModel

abstract class ForgotPasswordScreenViewModel : ViewModel(){
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
    abstract fun sendRequestToChangePassword(form: ForgotPasswordModel)
    abstract fun failed(error: Throwable?)
    abstract fun success(success: String)
}