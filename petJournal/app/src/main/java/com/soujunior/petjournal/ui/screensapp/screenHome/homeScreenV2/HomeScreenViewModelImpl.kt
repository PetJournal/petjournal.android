package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV1
import com.soujunior.domain.use_case.preference.CheckNotificationPermissionRequestedUseCase
import com.soujunior.domain.use_case.preference.SetNotificationPermissionRequestedUseCase
import com.soujunior.domain.use_case.tag.GetListTagUseCase
import com.soujunior.domain.use_case.task.GetLocalTasksByPeriodUseCase
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
    private val getLocalTasksByPeriodUseCase: GetLocalTasksByPeriodUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getListTagUseCase: GetListTagUseCase,
    private val checkNotificationPermissionRequestedUseCase: CheckNotificationPermissionRequestedUseCase,
    private val setNotificationPermissionRequestedUseCase: SetNotificationPermissionRequestedUseCase,
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
            getGuardianName(false)
            getPetList(false)
            getTask(false)
            getTags(false)
        }
    }

    override fun getGuardianName(forceRequest: Boolean) {
        _state.value = _state.value.copy(isLoadingUserName = true)
        viewModelScope.launch {
            val result = getGuardianNameUseCase.execute(forceRequest)
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
            is HomeEvent.ReloadListPet -> getPetList(true)
            is HomeEvent.ReloadListTag -> getTags(true)
            is HomeEvent.ReloadAll -> {
                getGuardianName(true)
                getPetList(true)
                getTask(true)
                getTags(true)
            }
        }
    }

    private fun getPetList(forceRequest: Boolean = false) {
        _state.value = _state.value.copy(isLoadingListPet = true)
        viewModelScope.launch {
            val result = getPetListUseCase.execute(forceRequest)
            result.handleResult({
                _state.value = _state.value.copy(listPets = it, isLoadingListPet = false)
            }, {
                _state.value = _state.value.copy(isLoadingListPet = false, hasErrorOnListPets = true)
            })
        }
    }

    private fun getTask(forceRequest: Boolean = false) {
        _state.value = _state.value.copy(isLoadingListTask = true)
        viewModelScope.launch {
            val today = java.time.LocalDate.now()
            val startDate = today.atStartOfDay().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val endDate = today.atTime(java.time.LocalTime.MAX).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            val input =
                GetLocalTasksByPeriodUseCase.Input(
                    startAt = startDate,
                    endAt = endDate,
                    considerTime = true,
                )
            val result = getLocalTasksByPeriodUseCase.execute(input)
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

    private fun getTags(forceRequest: Boolean = false) {
        _state.value = _state.value.copy(isLoadingListTag = true, hasErrorOnListTag = false)
        viewModelScope.launch {
            val result = getListTagUseCase.execute(forceRequest)
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

    override fun checkNotificationPermission(onShouldRequest: () -> Unit) {
        viewModelScope.launch {
            if (!checkNotificationPermissionRequestedUseCase()) {
                onShouldRequest()
                setNotificationPermissionRequestedUseCase(true)
            }
        }
    }
}
