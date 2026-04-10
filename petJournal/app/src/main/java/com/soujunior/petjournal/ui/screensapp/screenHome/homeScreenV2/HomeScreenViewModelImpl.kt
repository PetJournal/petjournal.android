package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV1
import com.soujunior.domain.use_case.tag.GetListTagUseCase
import com.soujunior.domain.use_case.task.GetListCurrentWeekTaskUseCase
import com.soujunior.petjournal.ui.mapper.Mapper
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModelImpl(
    private val getGuardianNameUseCase: GetGuardianNameUseCase,
    private val getPetListUseCase: GetListPetUseCaseV1,
    private val getListCurrentWeekTaskUseCase: GetListCurrentWeekTaskUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getListTagUseCase: GetListTagUseCase,
) : HomeScreenViewModel() {
    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()

    private fun updateName(newName: GuardianNameResponse) {
        _state.value = _state.value.copy(nameUser = newName.firstName)
    }

    override val message: StateFlow<String> get() = _message
    private val _message = MutableStateFlow("")

    private fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }

    override fun success(name: GuardianNameResponse) {
        updateName(name)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        updateMessage(exception?.message.toString())
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    init {
        viewModelScope.launch {
            getGuardianName()
            getPetList()
            getTask()
            getTags()
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

                _state.value = _state.value.copy(hasErrorOnNameUser = false)
            })
            _state.value = _state.value.copy(isLoadingUserName = false)
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ReloadListPet -> getPetList()
            is HomeEvent.ReloadListTag -> getTags()
            is HomeEvent.ReloadAll -> {
                getGuardianName()
                getPetList()
                getTask()
                getTags()
            }
        }
    }

    private fun getPetList() {
        _state.value = _state.value.copy(isLoadingListPet = true)
        viewModelScope.launch {
            val result = getPetListUseCase.execute(Unit)
            result.handleResult({
                _state.value = _state.value.copy(listPets = it, isLoadingListPet = false)
            }, {
                _state.value = _state.value.copy(isLoadingListPet = false, hasErrorOnListPets = true)
            })
        }
    }

    private fun getTask() {
        _state.value = _state.value.copy(isLoadingListTask = true)
        viewModelScope.launch {
            val result = getListCurrentWeekTaskUseCase.execute(Unit)
            result.handleResult({ value: PaginatedScheduleResponseModel ->
                _state.update {
                    with(Mapper) {
                        it.copy(
                            listScheduled = value,
                            listTaskData = value.toListOfTaskData(),
                            isLoadingListTask = false,
                        )
                    }
                }
            }, {
                _state.value = _state.value.copy(isLoadingListTask = false)
            })
        }
    }

    private fun getTags() {
        _state.value = _state.value.copy(isLoadingListTag = true, hasErrorOnListTag = false)
        viewModelScope.launch {
            val result = getListTagUseCase.execute(Unit)
            result.handleResult({ tags ->
                _state.value =
                    with(Mapper) {
                        _state.value.copy(
                            listTag = tags.map { it.toTagOption() },
                            isLoadingListTag = false,
                        )
                    }
            }, {
                _state.value =
                    _state.value.copy(
                        isLoadingListTag = false,
                        hasErrorOnListTag = true,
                    )
            })
        }
    }

    override fun logout() {
        viewModelScope.launch {
            logoutUseCase.doWork()
        }
    }
}
