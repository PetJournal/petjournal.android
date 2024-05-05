package com.soujunior.petjournal.ui.screens_app.screens_apresentation.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.auth.CheckLoginStatusUseCase
import com.soujunior.domain.use_case.base.DataResult
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase
) : ViewModel() {
    val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        checkLoginStatus()
    }

    private fun success(resulMessage: String) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    private fun failure() {
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val result = checkLoginStatusUseCase.execute(Unit)
            if (result is DataResult.Success) success("User is already logged in!")
            else failure()
        }
    }
}