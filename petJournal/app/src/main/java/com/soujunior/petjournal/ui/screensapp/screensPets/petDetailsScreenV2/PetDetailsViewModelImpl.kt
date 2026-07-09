package com.soujunior.petjournal.ui.screensapp.screensPets.petDetailsScreenV2

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.use_case.pet.GetPetByIdUseCase
import com.soujunior.domain.use_case.task.GetNextEventsForPetParams
import com.soujunior.domain.use_case.task.GetNextEventsForPetUseCase
import com.soujunior.petjournal.ui.mapper.Mapper.toTaskData
import com.soujunior.petjournal.ui.model.TaskData
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PetDetailsState(
    val pet: PetDetailsDTO? = null,
    val nextTasks: List<TaskData> = emptyList(),
)

abstract class PetDetailsViewModel : ViewModel() {
    abstract val state: StateFlow<PetDetailsState>
    abstract val taskState: StateFlow<TaskState>
    abstract val validationEventChannel: Channel<ValidationEvent>

    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun getPetDetails(id: String)

    abstract fun onDeleteTask(id: String)

    abstract fun failed(exception: Throwable?)
}

class PetDetailsViewModelImpl(
    private val savedStateHandle: SavedStateHandle,
    private val getPetByIdUseCase: GetPetByIdUseCase,
    private val getNextEventsForPetUseCase: GetNextEventsForPetUseCase,
    private val deleteTasksByIdUseCase: com.soujunior.domain.use_case.task.DeleteTasksByIdUseCase,
) : PetDetailsViewModel() {
    private val idPetFromRoute: String? = savedStateHandle.get<String>("idPet")

    private val _state = MutableStateFlow(PetDetailsState())
    override val state: StateFlow<PetDetailsState> = _state.asStateFlow()

    private val _taskState = MutableStateFlow<TaskState>(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()

    init {
        if (!idPetFromRoute.isNullOrBlank()) {
            getPetDetails(idPetFromRoute)
        } else {
            _taskState.value = TaskState.Idle
        }
    }

    override fun getPetDetails(id: String) {
        _taskState.value = TaskState.Loading

        viewModelScope.launch {
            val petDetailsDeferred = async { getPetByIdUseCase.execute(id) }
            val nextEventsDeferred = async { getNextEventsForPetUseCase.execute(GetNextEventsForPetParams(petId = id)) }

            val petResult = petDetailsDeferred.await()
            val nextEventsResult = nextEventsDeferred.await()

            petResult.handleResult(
                success = { pet ->
                    _state.update { it.copy(pet = pet) }
                    Log.e(TAG, "OBJETO RECEBIDO: $pet")
                },
                error = { failed(it) },
            )

            nextEventsResult.handleResult(
                success = { response ->
                    _state.update { it.copy(nextTasks = response.nextEvents.toTaskData()) }
                },
                error = {
                    Log.e(TAG, "Error fetching next tasks: $it")
                },
            )

            _taskState.value = TaskState.Idle
        }
    }

    override fun onDeleteTask(id: String) {
        viewModelScope.launch {
            val result = deleteTasksByIdUseCase.execute(id)
            result.handleResult(
                success = {
                    idPetFromRoute?.let { getPetDetails(it) }
                },
                error = {
                    Log.e(TAG, "Error deleting task: $it")
                },
            )
        }
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }
}

class FakePetDetailsViewModel : PetDetailsViewModel() {
    override val state =
        MutableStateFlow(
            PetDetailsState(
                pet =
                    PetDetailsDTO(
                        id = "1",
                        petName = "Rex",
                        specieAlias = "Cachorro",
                        gender = "Macho",
                        breedAlias = "Golden Retriever",
                        image = null,
                    ),
                nextTasks = emptyList(),
            ),
        ).asStateFlow()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle).asStateFlow()
    override val validationEventChannel = Channel<ValidationEvent>()

    override fun getPetDetails(id: String) { /* No-op */ }

    override fun onDeleteTask(id: String) { /* No-op */ }

    override fun failed(exception: Throwable?) { /* No-op */ }
}
