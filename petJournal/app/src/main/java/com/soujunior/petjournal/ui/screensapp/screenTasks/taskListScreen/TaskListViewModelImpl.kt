package com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.use_case.task.DeleteAllTheseTaskByIdUseCase
import com.soujunior.domain.use_case.task.DeleteOnlyThisTaskByIdUseCase
import com.soujunior.domain.use_case.task.GetListCurrentDateTaskUseCase
import com.soujunior.domain.use_case.task.GetListCurrentMonthTaskUseCase
import com.soujunior.domain.use_case.task.GetListCurrentWeekTaskUseCase
import com.soujunior.petjournal.ui.mapper.Mapper
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskListViewModelImpl(
    private val getListCurrentDateTaskUseCase: GetListCurrentDateTaskUseCase,
    private val getListCurrentWeekTaskUseCase: GetListCurrentWeekTaskUseCase,
    private val getListCurrentMonthTaskUseCase: GetListCurrentMonthTaskUseCase,
    private val deleteOnlyThisTaskById: DeleteOnlyThisTaskByIdUseCase,
    private val deleteAllTheseTaskById: DeleteAllTheseTaskByIdUseCase,
) : TaskListViewModel() {
    private val _state = MutableStateFlow(TaskListState(isLoading = false))
    override val state: StateFlow<TaskListState> = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()

    init {
        loadTasks(filter = DateFilter.DAILY, forceRequest = false, isSilent = false)
    }

    override fun success(name: GuardianNameResponse) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun onEvent(event: TaskListEvent) {
        when (event) {
            is TaskListEvent.OnDateFilterChange -> loadTasks(event.dateFilter, isSilent = false)
            is TaskListEvent.OnRefresh -> loadTasks(_state.value.selectedDateFilter, forceRequest = true, isSilent = false)
            is TaskListEvent.AddTaskButton -> { /* ... */ }
            is TaskListEvent.OnDeleteOnlyThisTask -> {
                viewModelScope.launch {
                    val result = deleteOnlyThisTaskById.execute(event.id)
                    result.handleResult({
                        loadTasks(
                            filter = _state.value.selectedDateFilter,
                            forceRequest = true,
                            isSilent = false,
                        )
                    }, { error ->
                        Log.e("TaskListViewModel", "Erro ao deletar task: ${error?.message}", error)
                    })
                }
            }
            is TaskListEvent.OnDeleteAllTheseTask -> {
                viewModelScope.launch {
                    val result = deleteAllTheseTaskById.execute(event.schedulerId)
                    result.handleResult({
                        loadTasks(
                            filter = _state.value.selectedDateFilter,
                            forceRequest = true,
                            isSilent = false,
                        )
                    }, { error ->
                        Log.e("TaskListViewModel", "Erro ao deletar task: ${error?.message}", error)
                    })
                }
            }
        }
    }

    override fun onResume() {
        loadTasks(_state.value.selectedDateFilter, forceRequest = false, isSilent = true)
    }

    private fun loadTasks(
        filter: DateFilter,
        forceRequest: Boolean = false,
        isSilent: Boolean = false,
    ) {
        if (!isSilent) {
            _state.update { it.copy(isLoading = true, error = null, selectedDateFilter = filter) }
        } else {
            _state.update { it.copy(error = null, selectedDateFilter = filter) }
        }

        viewModelScope.launch {
            val result =
                when (filter) {
                    DateFilter.DAILY -> getListCurrentDateTaskUseCase.execute(forceRequest)
                    DateFilter.WEEKLY -> getListCurrentWeekTaskUseCase.execute(forceRequest)
                    DateFilter.MONTHLY -> getListCurrentMonthTaskUseCase.execute(forceRequest)
                }

            result.handleResult({ value: PaginatedScheduleResponseModel ->
                _state.update {
                    with(Mapper) {
                        it.copy(
                            tasks = value.toListOfTaskData(),
                            isLoading = false,
                        )
                    }
                }
            }, {
                _state.update { state -> state.copy(isLoading = false, error = it?.message) }
            })
        }
    }
}
