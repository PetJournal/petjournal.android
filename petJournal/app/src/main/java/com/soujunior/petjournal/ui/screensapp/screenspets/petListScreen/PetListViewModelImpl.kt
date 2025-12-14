package com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetListViewModelImpl : PetListViewModel() {
    override val message: StateFlow<String> get() = TODO("Not yet implemented")
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents: Flow<ValidationEvent>
        get() = super.validationEvents

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        _taskState.value = TaskState.Idle
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
}
