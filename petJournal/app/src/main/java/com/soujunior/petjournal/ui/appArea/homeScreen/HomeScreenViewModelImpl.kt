package com.soujunior.petjournal.ui.appArea.homeScreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.usecase.auth.GetUserDataUseCase
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class HomeScreenViewModelImpl(): HomeScreenViewModel(){

    override var state = HomeState()
    override val validationEventChannel = Channel<ValidationEvent>()
    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()
    override fun success(name: String) {
        this.success.value = name
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
            this.error.value = exception.message
        } else {
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
            this.error.value = "Falha ao recuperar nome de usuario"
        }
    }


}