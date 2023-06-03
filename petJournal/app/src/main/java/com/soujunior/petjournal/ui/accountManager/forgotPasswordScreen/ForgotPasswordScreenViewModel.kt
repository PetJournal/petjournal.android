package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ForgotPasswordScreenViewModel : ViewModel(){
    abstract var state: ForgotPassFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(resultPostSubmit: String)
    abstract fun failed(exception: Throwable?)
      abstract fun onEvent(event: ForgotPassFormEvent.EmailChanged)
    abstract fun submitData()

    abstract fun onEvent(event: ForgotPassFormEvent)
}