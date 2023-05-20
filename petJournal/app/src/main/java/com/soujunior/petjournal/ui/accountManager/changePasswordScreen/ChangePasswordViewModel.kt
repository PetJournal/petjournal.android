package com.soujunior.petjournal.ui.accountManager.changePasswordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.PasswordModel

abstract class ChangePasswordViewModel : ViewModel(){
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
    abstract fun failed(exception: Throwable?)
    abstract fun success(result: String)
    abstract fun sendNewPassword(newPassword: PasswordModel)
    abstract fun disconectOtherDevices()
}