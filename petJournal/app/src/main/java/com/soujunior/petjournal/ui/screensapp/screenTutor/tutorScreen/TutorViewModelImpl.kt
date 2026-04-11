package com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianEmailUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TutorViewModelImpl(
    private val getGuardianNameUseCase: GetGuardianNameUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getGuardianEmailUseCase: GetGuardianEmailUseCase,
) : TutorViewModel() {
    private val _state = MutableStateFlow(TutorState())
    override val state: StateFlow<TutorState> get() = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()

    init {
        getGuardianName()
        getLoginPreference()
    }

    private fun getLoginPreference() {
        viewModelScope.launch {
            val result = getGuardianEmailUseCase.execute(Unit)
            result.handleResult({ email ->
                if (!email.isNullOrBlank()) {
                    _state.value = _state.value.copy(email = email)
                }
            }, {
                // Falha silenciosa: mantém email "" ou não exibe nada
            })
        }
    }

    override fun success(name: GuardianNameResponse) {
        _state.value = _state.value.copy(nameUser = name.firstName)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun getGuardianName() {
        _state.value = _state.value.copy(isLoadingUserName = true)
        viewModelScope.launch {
            val result = getGuardianNameUseCase.execute(Unit)
            result.handleResult({
                success(it)
            }, {
                failed(it)
                _state.value = _state.value.copy(hasErrorOnNameUser = true)
            })
            _state.value = _state.value.copy(isLoadingUserName = false)
        }
    }

    override fun logout() {
        viewModelScope.launch {
            logoutUseCase.doWork()
        }
    }
}
