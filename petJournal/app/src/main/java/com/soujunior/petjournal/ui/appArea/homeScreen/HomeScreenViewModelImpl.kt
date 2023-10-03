package com.soujunior.petjournal.ui.appArea.homeScreen


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModelImpl(private val getGuardianNameUseCase: GetGuardianNameUseCase,
                              private val logoutUseCase: LogoutUseCase) : HomeScreenViewModel() {
    override var state = HomeState()
    override val validationEventChannel = Channel<ValidationEvent>()

    override val name: StateFlow<GuardianNameResponse> get() = _name
    private val _name = MutableStateFlow(GuardianNameResponse("", ""))

    fun updateName(newName: GuardianNameResponse) {
        _name.value = newName
    }

    override val message: StateFlow<String> get() = _message
    private val _message = MutableStateFlow("")

    fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }

    override fun success(name: GuardianNameResponse) {
        updateName(name)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        updateMessage(exception?.message.toString() ?: "Erro desconhecido!")
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }
    init{getData()}
    override fun getData() {
        viewModelScope.launch {
            val result = getGuardianNameUseCase.execute(Unit)
            result.handleResult(::success, ::failed)
        }
    }

    override fun logout() {
        viewModelScope.launch {
            logoutUseCase.doWork()
        }
    }
}