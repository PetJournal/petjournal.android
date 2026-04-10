package com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.use_case.auth.LogoutUseCase
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
) : TutorViewModel() {
    private val _state = MutableStateFlow(TutorState())
    override val state: StateFlow<TutorState> get() = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()

    init {
        getGuardianName()
    }

    override fun success(name: GuardianNameResponse) {
        _state.value = _state.value.copy(nameUser = name.firstName)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        Log.e(TAG, "failed: ${exception?.message}")
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
                Log.e(TAG, "getGuardianName error: $it")
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
