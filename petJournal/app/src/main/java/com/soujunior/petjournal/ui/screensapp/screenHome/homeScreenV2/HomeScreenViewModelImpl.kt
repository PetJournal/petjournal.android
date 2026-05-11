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
import com.soujunior.domain.use_case.task.GetListCurrentDateTaskUseCase
import com.soujunior.petjournal.ui.mapper.Mapper
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModelImpl(
    private val getGuardianNameUseCase: GetGuardianNameUseCase,
    private val getPetListUseCase: GetListPetUseCaseV1,
    private val logoutUseCase: LogoutUseCase,
    private val getListTagUseCase: GetListTagUseCase,
    private val checkNotificationPermissionRequestedUseCase: CheckNotificationPermissionRequestedUseCase,
    private val setNotificationPermissionRequestedUseCase: SetNotificationPermissionRequestedUseCase,
    private val getListCurrentDateTaskUseCase: GetListCurrentDateTaskUseCase,
) : HomeScreenViewModel() {

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()

    private fun updateName(newName: GuardianNameResponse) {
        _state.update { it.copy(nameUser = newName.firstName) }
    }

    override val message: StateFlow<String> get() = _message
    private val _message = MutableStateFlow("")

    private fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }

    private var guardianJob: Job? = null
    private var petJob: Job? = null
    private var taskJob: Job? = null
    private var tagJob: Job? = null

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
        // Inicialização normal (COM Shimmer de carregamento)
        getGuardianNameInternal(forceRequest = false, isSilent = false)
        getPetList(forceRequest = false, isSilent = false)
        getTasks(forceRequest = false, isSilent = false)
        getTags(forceRequest = false, isSilent = false)
    }

    override fun getGuardianName(forceRequest: Boolean) {
        getGuardianNameInternal(forceRequest, isSilent = false)
    }

    private fun getGuardianNameInternal(forceRequest: Boolean, isSilent: Boolean) {
        guardianJob?.cancel()
        if (!isSilent) _state.update { it.copy(isLoadingUserName = true, hasErrorOnNameUser = false) }

        guardianJob = viewModelScope.launch {
            val result = getGuardianNameUseCase.execute(forceRequest)
            result.handleResult({
                success(it)
                _state.update { state -> state.copy(isLoadingUserName = false) }
            }, { error ->
                if (error is CancellationException) throw error
                failed(error)
                _state.update { state -> state.copy(isLoadingUserName = false, hasErrorOnNameUser = true) }
            })
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ReloadListPet -> getPetList(forceRequest = true, isSilent = false)
            is HomeEvent.ReloadListTag -> getTags(forceRequest = true, isSilent = false)
            is HomeEvent.ReloadAll -> {
                getGuardianNameInternal(forceRequest = true, isSilent = false)
                getPetList(forceRequest = true, isSilent = false)
                getTasks(forceRequest = true, isSilent = false)
                getTags(forceRequest = true, isSilent = false)
            }
            // NOVO FLUXO: Atualização 100% invisível baseada no Cache (Short-Circuit fará o trabalho duro)
            is HomeEvent.SilentRefresh -> {
                getGuardianNameInternal(forceRequest = false, isSilent = true)
                getPetList(forceRequest = false, isSilent = true)
                getTasks(forceRequest = false, isSilent = true)
                getTags(forceRequest = false, isSilent = true)
            }
        }
    }

    private fun getPetList(forceRequest: Boolean = false, isSilent: Boolean = false) {
        petJob?.cancel()
        if (!isSilent) _state.update { it.copy(isLoadingListPet = true, hasErrorOnListPets = false) }

        petJob = viewModelScope.launch {
            val result = getPetListUseCase.execute(forceRequest)
            result.handleResult({ pets ->
                _state.update { it.copy(listPets = pets, isLoadingListPet = false) }
            }, { error ->
                if (error is CancellationException) throw error
                _state.update { it.copy(isLoadingListPet = false, hasErrorOnListPets = true) }
            })
        }
    }

    private fun getTasks(forceRequest: Boolean = false, isSilent: Boolean = false) {
        taskJob?.cancel()
        if (!isSilent) _state.update { it.copy(isLoadingListTask = true) }

        taskJob = viewModelScope.launch {
            val result = getListCurrentDateTaskUseCase.execute(forceRequest)
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
            }, { error ->
                if (error is CancellationException) throw error
                _state.update { it.copy(isLoadingListTask = false) }
            })
        }
    }

    private fun getTags(forceRequest: Boolean = false, isSilent: Boolean = false) {
        tagJob?.cancel()
        if (!isSilent) _state.update { it.copy(isLoadingListTag = true, hasErrorOnListTag = false) }

        tagJob = viewModelScope.launch {
            val result = getListTagUseCase.execute(forceRequest)
            result.handleResult({ tags ->
                _state.update {
                    with(Mapper) {
                        it.copy(
                            listTag = tags.map { tag -> tag.toTagOption() },
                            isLoadingListTag = false,
                        )
                    }
                }
            }, { error ->
                if (error is CancellationException) throw error
                _state.update { it.copy(isLoadingListTag = false, hasErrorOnListTag = true) }
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