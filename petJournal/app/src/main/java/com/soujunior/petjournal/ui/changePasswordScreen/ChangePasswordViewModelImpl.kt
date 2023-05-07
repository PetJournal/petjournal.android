package com.soujunior.petjournal.ui.changePasswordScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.PasswordModel
import com.soujunior.domain.usecase.auth.ChangePasswordUseCase
import kotlinx.coroutines.launch

class ChangePasswordViewModelImpl(
    private val changePassword: ChangePasswordUseCase
) : ChangePasswordViewModel() {
    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()

    override fun success(result: String) {
        this.success.value = result
    }
    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            this.error.value = exception.message
        } else {
            this.error.value = "lançar um erro aqui"
        }
    }

    override fun sendNewPassword(newPassword: PasswordModel) {
        viewModelScope.launch {
            val result = changePassword.execute(newPassword)
            result.handleResult(::success, ::failed)
        }
    }

    override fun disconectOtherDevices() {}
}