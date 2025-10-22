package com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.PetFormEvent
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.PetFormState
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.ViewModelChoiceSpecies
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.states._root_ide_package_.com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.TaskListState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow


class FakeTaskListViewModel : TaskListViewModel() {
    override var state: TaskListState
        get() = PetFormState()
        set(value) {}
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()
    override fun success(name: GuardianNameResponse) {}

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun failed(exception: Throwable?) { /* No-op */
    }

    override fun onEvent(event: PetFormEvent) {}


    override fun enableButton(): Boolean = true


    override fun change(specieSelected: String?, specieWritten: String?) {}

    override fun savePetInformation(specie: String) {}

}

abstract class TaskListViewModel : ViewModel() {
    abstract var state: TaskListState
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()
    abstract val taskState: StateFlow<TaskListState>

    abstract fun success(name: GuardianNameResponse)
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: TaskListEvent)

    // visualiza a tarefa baeado em periodos temporais (dia/mes/ano)
    // cria tarefa
}