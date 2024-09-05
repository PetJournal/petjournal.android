package com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.guardian.GetPetRegistrationWentLive
import com.soujunior.domain.use_case.guardian.SetPetRegistrationWentLive
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IntroRegisterPetViewModelImpl(
    private val setWasViewed: SetPetRegistrationWentLive,
    private val getWasViewed: GetPetRegistrationWentLive,
    private val getName: GetGuardianNameUseCase,
) : IntroRegisterPetViewModel() {
    override val name: StateFlow<String?> get() = _name
    private val _name = MutableStateFlow("")
    override val visualizedScreen: StateFlow<Boolean> get() = _visualizedScreen
    private val _visualizedScreen = MutableStateFlow(false)
    override val message: StateFlow<String> get() = TODO("Not yet implemented")
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents: Flow<ValidationEvent>
        get() = super.validationEvents

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        _taskState.value = TaskState.Loading
        getWasViewed()
    }

    override fun success() {
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }
    }

    override fun failed(exception: Throwable?) {
        exception?.message?.let { Log.e(TAG, it) }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    private fun verification(state: Boolean) {
        if (!state) {
            _visualizedScreen.value = false
            getName()
        } else {
            _visualizedScreen.value = true
            success()
        }
    }

    override fun getWasViewed() {
        viewModelScope.launch {
            try {
                val result = getWasViewed.execute(Unit)
                result.handleResult(::verification, ::failed)
            } catch (e: Exception) {
                Log.e(TAG, "Erro: $e")
            }
        }
    }

    override fun setWasViewed() {
        viewModelScope.launch {
            val result = setWasViewed.execute(true)
            result.handleResult({ verification(true) }, ::failed)
        }
    }

    override fun getName() {
        viewModelScope.launch {
            val result = getName.execute(Unit)
            result.handleResult(
                { name -> run { _name.value = name.firstName } },
                { error -> run { Log.e(TAG, "Error ->>: $error") } })
            _taskState.value = TaskState.Idle
        }
    }
}