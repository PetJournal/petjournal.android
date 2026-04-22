package com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
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
) : TaskListViewModel() {
    private val _state = MutableStateFlow(TaskListState(isLoading = true))
    override val state: StateFlow<TaskListState> = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()

    init {
        onEvent(TaskListEvent.OnDateFilterChange(DateFilter.DAILY))
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
            is TaskListEvent.OnDateFilterChange -> loadTasks(event.dateFilter)
            is TaskListEvent.AddTaskButton -> {
                // To be implemented using Navigation
            }
        }
    }

    private fun loadTasks(filter: DateFilter) {
        _state.update { it.copy(isLoading = true, error = null, selectedDateFilter = filter) }
        viewModelScope.launch {
            val result =
                when (filter) {
                    DateFilter.DAILY -> getListCurrentDateTaskUseCase.execute(Unit)
                    DateFilter.WEEKLY -> getListCurrentWeekTaskUseCase.execute(false)
                    DateFilter.MONTHLY -> getListCurrentMonthTaskUseCase.execute(Unit)
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
