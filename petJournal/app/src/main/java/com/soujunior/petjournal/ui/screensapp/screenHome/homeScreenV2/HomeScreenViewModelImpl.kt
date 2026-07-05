package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV1
import com.soujunior.domain.use_case.preference.CheckNotificationPermissionRequestedUseCase
import com.soujunior.domain.use_case.preference.SetNotificationPermissionRequestedUseCase
import com.soujunior.domain.use_case.tag.GetListTagUseCase
import com.soujunior.domain.use_case.task.DeleteTasksByIdUseCase
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
    private val deleteTaskUseCase: DeleteTasksByIdUseCase,
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
        getGuardianNameInternal(forceRequest = false, isSilent = false)
        getPetList(forceRequest = false, isSilent = false)
        getTasks(forceRequest = false, isSilent = false)
        getTags(forceRequest = false, isSilent = false)
    }

    override fun getGuardianName(forceRequest: Boolean) {
        getGuardianNameInternal(forceRequest, isSilent = false)
    }

    private fun getGuardianNameInternal(
        forceRequest: Boolean,
        isSilent: Boolean,
    ) {
        guardianJob?.cancel()
        if (!isSilent) _state.update { it.copy(isLoadingUserName = true, hasErrorOnNameUser = false) }

        guardianJob =
            viewModelScope.launch {
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
                guardianJob?.cancel()
                petJob?.cancel()
                taskJob?.cancel()
                tagJob?.cancel()

                _state.update { it.copy(isSyncingBackground = true) }

                viewModelScope.launch {
                    val localNameJob =
                        launch {
                            getGuardianNameUseCase.executeLocalOnly().handleResult({
                                updateName(it)
                                _state.update { s -> s.copy(isLoadingUserName = false) }
                            }, {
                                _state.update { s -> s.copy(isLoadingUserName = false) }
                            })
                        }
                    val localPetsJob =
                        launch {
                            getPetListUseCase.executeLocalOnly().handleResult({ pets ->
                                _state.update { s -> s.copy(listPets = pets, isLoadingListPet = false) }
                            }, {
                                _state.update { s -> s.copy(isLoadingListPet = false) }
                            })
                        }
                    val localTasksJob =
                        launch {
                            getListCurrentDateTaskUseCase.executeLocalOnly().handleResult({ value ->
                                val taskDataList = with(Mapper) { value.toListOfTaskData() }.distinctBy { it.id }
                                _state.update { s ->
                                    s.copy(
                                        listScheduled = value,
                                        listTaskData = taskDataList,
                                        isLoadingListTask = false,
                                    )
                                }
                            }, {
                                _state.update { s -> s.copy(isLoadingListTask = false) }
                            })
                        }
                    val localTagsJob =
                        launch {
                            getListTagUseCase.executeLocalOnly().handleResult({ tags ->
                                _state.update { s ->
                                    with(Mapper) {
                                        s.copy(
                                            listTag = tags.map { tag -> tag.toTagOption() },
                                            isLoadingListTag = false,
                                        )
                                    }
                                }
                            }, {
                                _state.update { s -> s.copy(isLoadingListTag = false) }
                            })
                        }

                    localNameJob.join()
                    localPetsJob.join()
                    localTasksJob.join()
                    localTagsJob.join()

                    val remoteNameJob =
                        launch {
                            getGuardianNameUseCase.execute(true).handleResult({
                                updateName(it)
                                _state.update { s -> s.copy(isLoadingUserName = false) }
                            }, { error ->
                                if (error is CancellationException) throw error
                                failed(error)
                                _state.update { s -> s.copy(isLoadingUserName = false, hasErrorOnNameUser = true) }
                            })
                        }
                    guardianJob = remoteNameJob

                    val remotePetsJob =
                        launch {
                            getPetListUseCase.execute(true).handleResult({ pets ->
                                _state.update { s -> s.copy(listPets = pets, isLoadingListPet = false) }
                            }, { error ->
                                if (error is CancellationException) throw error
                                _state.update { s -> s.copy(isLoadingListPet = false, hasErrorOnListPets = true) }
                            })
                        }
                    petJob = remotePetsJob

                    val remoteTasksJob =
                        launch {
                            getListCurrentDateTaskUseCase.execute(true).handleResult({ value ->
                                val taskDataList = with(Mapper) { value.toListOfTaskData() }.distinctBy { it.id }
                                _state.update { s ->
                                    s.copy(
                                        listScheduled = value,
                                        listTaskData = taskDataList,
                                        isLoadingListTask = false,
                                    )
                                }
                            }, { error ->
                                if (error is CancellationException) throw error
                                _state.update { s -> s.copy(isLoadingListTask = false) }
                            })
                        }
                    taskJob = remoteTasksJob

                    val remoteTagsJob =
                        launch {
                            getListTagUseCase.execute(true).handleResult({ tags ->
                                _state.update { s ->
                                    with(Mapper) {
                                        s.copy(
                                            listTag = tags.map { tag -> tag.toTagOption() },
                                            isLoadingListTag = false,
                                        )
                                    }
                                }
                            }, { error ->
                                if (error is CancellationException) throw error
                                _state.update { s -> s.copy(isLoadingListTag = false, hasErrorOnListTag = true) }
                            })
                        }
                    tagJob = remoteTagsJob

                    remoteNameJob.join()
                    remotePetsJob.join()
                    remoteTasksJob.join()
                    remoteTagsJob.join()

                    _state.update { s -> s.copy(isSyncingBackground = false) }
                }
            }
            is HomeEvent.SilentRefresh -> {
                getGuardianNameInternal(forceRequest = false, isSilent = true)
                getPetList(forceRequest = false, isSilent = true)
                getTasks(forceRequest = false, isSilent = true)
                getTags(forceRequest = false, isSilent = true)
            }
            is HomeEvent.OnDeleteTask -> {
                viewModelScope.launch {
                    val result = deleteTaskUseCase.execute(event.id)
                    result.handleResult({
                        _state.update { currentState ->
                            val updatedTaskDataList = currentState.listTaskData?.filter { it.id != event.id }
                            currentState.copy(listTaskData = updatedTaskDataList)
                        }
                        getTasks(forceRequest = false, isSilent = true)
                    }, { error ->
                        Log.e("HomeScreenViewModel", "Erro ao deletar task: ${error?.message}", error)
                    })
                }
            }
        }
    }

    private fun getPetList(
        forceRequest: Boolean = false,
        isSilent: Boolean = false,
    ) {
        petJob?.cancel()
        if (!isSilent) _state.update { it.copy(isLoadingListPet = true, hasErrorOnListPets = false) }

        petJob =
            viewModelScope.launch {
                val result = getPetListUseCase.execute(forceRequest)
                result.handleResult({ pets ->
                    _state.update { it.copy(listPets = pets, isLoadingListPet = false) }
                }, { error ->
                    if (error is CancellationException) throw error
                    _state.update { it.copy(isLoadingListPet = false, hasErrorOnListPets = true) }
                })
            }
    }

    private fun getTasks(
        forceRequest: Boolean = false,
        isSilent: Boolean = false,
    ) {
        if (taskJob?.isActive == true && !forceRequest) return
        taskJob?.cancel()
        Log.d("HomeScreenViewModel", "getTasks: Iniciando carregamento. forceRequest=$forceRequest, isSilent=$isSilent")
        if (!isSilent) _state.update { it.copy(isLoadingListTask = true) }

        taskJob =
            viewModelScope.launch {
                val result = getListCurrentDateTaskUseCase.execute(forceRequest)
                result.handleResult({ value: PaginatedScheduleResponseModel ->
                    Log.d("HomeScreenViewModel", "getTasks: Sucesso. Recebidas ${value.data?.size ?: 0} tarefas brutas da API/Cache.")
                    val taskDataList = with(Mapper) { value.toListOfTaskData() }.distinctBy { it.id }
                    Log.d(
                        "HomeScreenViewModel",
                        "getTasks: Mapeamento concluído (deduplicado). ${taskDataList.size} tarefas prontas para exibição.",
                    )
                    _state.update {
                        it.copy(
                            listScheduled = value,
                            listTaskData = taskDataList,
                            isLoadingListTask = false,
                        )
                    }
                }, { error ->
                    if (error is CancellationException) {
                        Log.d("HomeScreenViewModel", "getTasks: Corotina cancelada.")
                        throw error
                    }
                    Log.e("HomeScreenViewModel", "getTasks: Falha ao obter tarefas.", error)
                    _state.update { it.copy(isLoadingListTask = false) }
                })
            }
    }

    private fun getTags(
        forceRequest: Boolean = false,
        isSilent: Boolean = false,
    ) {
        tagJob?.cancel()
        if (!isSilent) _state.update { it.copy(isLoadingListTag = true, hasErrorOnListTag = false) }

        tagJob =
            viewModelScope.launch {
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
