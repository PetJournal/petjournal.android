package com.soujunior.petjournal.ui.appArea.pets.registerPetScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.guardian.GetPetRegistrationWentLive
import com.soujunior.domain.use_case.guardian.SetPetRegistrationWentLive
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterPetViewModelImpl(
    private val setWasViewed: SetPetRegistrationWentLive,
    private val getWasViewed: GetPetRegistrationWentLive,
) : RegisterPetViewModel() {
    override val visualizedScreen: StateFlow<Boolean> get() = _visualizedScreen
    private val _visualizedScreen = MutableStateFlow(false)
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents: Flow<ValidationEvent>
        get() = super.validationEvents

    init {
        getWasViewed()
    }

    override fun success(value: String) {
        Log.e(TAG, "success(): $value")
        _visualizedScreen.value = value.toBoolean()
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        exception?.message?.let { Log.e(TAG, it) }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun getWasViewed() {
        viewModelScope.launch {
            try {
                val result = getWasViewed.execute(Unit)
                result.handleResult({ it -> success(it.toString()) }, ::failed)
            } catch (e: Exception) {
                Log.e(TAG, "Erro: $e")
            }
        }
    }

    override fun setWasViewed() {
        viewModelScope.launch {
            Log.e(TAG, "botao clicado, inicio")
            val result = setWasViewed.execute(true)
            result.handleResult(::success, ::failed)
            Log.e(TAG, "botao clicado, fim")
        }
    }
}