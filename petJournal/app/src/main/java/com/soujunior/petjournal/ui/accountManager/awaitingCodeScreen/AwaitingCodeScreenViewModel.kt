package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.AwaitingCodeModel

abstract class AwaitingCodeScreenViewModel : ViewModel() {

    abstract val success: LiveData<String>
    abstract val error: LiveData<String>

    abstract fun postOtpVerification(code: AwaitingCodeModel)

    abstract fun failed(exception: Throwable?)
    abstract fun success(resultPostAwaitingCode: String)

}