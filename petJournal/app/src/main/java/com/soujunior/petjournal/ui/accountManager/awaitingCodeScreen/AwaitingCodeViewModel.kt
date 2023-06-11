package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

//TODO: (Leo) Se nao for mais precisar do comentario, retire
abstract class AwaitingCodeViewModel : ViewModel() {
    abstract var state: AwaitingCodeFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()
    //abstract fun postOtpVerification(code: AwaitingCodeModel)
    abstract fun postOtpVerification()
    abstract fun failed(exception: Throwable?)
    abstract fun success(resultPostAwaitingCode: String)
    abstract fun onEvent(event: AwaitingCodeFormEvent)
    abstract fun enableButton(): Boolean

}