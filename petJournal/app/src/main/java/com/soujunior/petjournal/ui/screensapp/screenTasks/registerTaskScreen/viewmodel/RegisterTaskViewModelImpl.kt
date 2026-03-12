package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV2
import com.soujunior.domain.use_case.task.CreateTagUseCase
import com.soujunior.domain.use_case.task.DeleteTagUseCase
import com.soujunior.domain.use_case.task.GetListTagUseCase
import com.soujunior.domain.use_case.task.UpdateTagUseCase
import com.soujunior.petjournal.ui.mapper.Mapper.toColor
import com.soujunior.petjournal.ui.mapper.Mapper.toListSelectableButtonInfo
import com.soujunior.petjournal.ui.mapper.Mapper.toPetsList
import com.soujunior.petjournal.ui.mapper.Mapper.uiModel
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskEvent
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskState
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class RegisterTaskViewModelImpl(
    private val getListTagCase: GetListTagUseCase,
    private val createTagCase: CreateTagUseCase,
    private val updateTagCase: UpdateTagUseCase,
    private val deleteTagCase: DeleteTagUseCase,
    private val getPetListUseCase: GetListPetUseCaseV2,
) : RegisterTaskViewModel() {
    private val _state = MutableStateFlow(RegisterTaskState())
    override val state: MutableStateFlow<RegisterTaskState> get() = _state

    init {
        getData()
    }

    private fun getData() {
        getTags()
        getPets()
    }

    override val validationEventChannel = Channel<ValidationEvent>()

    override fun onEvent(event: RegisterTaskEvent) {
        when (event) {
            is RegisterTaskEvent.OnCreateTag -> {
                createTag(event.name, event.color)
            }
            is RegisterTaskEvent.OnUpdateTag -> {
                updateTag(event.name, event.color, event.id)
            }
            is RegisterTaskEvent.OnDeleteTag -> {
                deleteTag(event.id)
            }
            is RegisterTaskEvent.OnName -> {
                _state.update { it.copy(taskName = event.name) }
            }
            is RegisterTaskEvent.OnDescription -> {
                _state.update { it.copy(taskDescription = event.text) }
            }
            is RegisterTaskEvent.OnPetList -> {
                _state.update { state ->
                    if (event.ids.isEmpty()) {
                        return@update state.copy(selectedPet = emptyList())
                    }
                    if (event.ids.firstOrNull() == "All") {
                        state.copy(selectedPet = emptyList())
                    } else {
                        val current = state.selectedPet
                        val toAdd = event.ids.filter { id -> !current.contains(id) }
                        val toRemove = event.ids.filter { id -> current.contains(id) }
                        val newList = (current + toAdd) - toRemove.toSet()
                        state.copy(selectedPet = newList)
                    }
                }
            }
            is RegisterTaskEvent.OnChangeTransactionType -> {
                _state.update { it.copy(selectedTransactionType = event.type) }
            }
            is RegisterTaskEvent.OnPeriodType -> {
                _state.update { it.copy(periodType = event.value) }
            }
            is RegisterTaskEvent.OnAmPm -> {
                _state.update { it.copy(amPmSelected = event.value) }
            }
            is RegisterTaskEvent.OnTimeChange -> {
                _state.update { it.copy(timeSelected = event.value) }
            }
            is RegisterTaskEvent.OnDateChanged -> {
                _state.update { it.copy(dateSelected = event.value) }
            }
            is RegisterTaskEvent.OnDayOfWeekChanged -> {
                val mList = state.value.selectedDaysOfWeek.toMutableList()
                if (mList.contains(event.value)) {
                    mList.remove(event.value)
                } else {
                    mList.add(event.value)
                }
                _state.update { it.copy(selectedDaysOfWeek = mList) }
            }
            is RegisterTaskEvent.OnDayChanged -> {
                event.value?.let { value ->
                    _state.update {
                        it.copy(
                            daySelected = value,
                            activeMonths = getMonthsWithSpecificDay(value),
                        )
                    }
                }
            }
            is RegisterTaskEvent.OnObservation -> {
                _state.update {
                    it.copy(observation = event.value)
                }
            }
            is RegisterTaskEvent.ReloadListPet -> {}
        }
    }

    private fun getMonthsWithSpecificDay(day: Int): List<Int> {
        if (day !in 1..31) return emptyList()

        val currentYear = LocalDate.now().year

        return (1..12).filter { month ->
            day <= YearMonth.of(currentYear, month).lengthOfMonth()
        }
    }

    private fun getTags() {
        _state.update { it.copy(isLoadingListTag = true) }
        viewModelScope.launch {
            val result = getListTagCase.execute(Unit)
            result.handleResult({ list: List<TagModel> ->
                _state.update {
                    it.copy(
                        isLoadingListTag = false,
                        listTag = list.toListSelectableButtonInfo(),
                    )
                }
            }, {
                _state.update { it.copy(isLoadingListTag = false, hasErrorOnListTag = true) }
            })
        }
    }

    private fun createTag(
        name: String,
        color: String,
    ) {
        _state.update { it.copy(isLoadingListTag = true) }
        viewModelScope.launch {
            val result = createTagCase.execute(TagModel(name = name, color = color))
            result.handleResult({ tagModel: TagModel ->
                _state.value.listTag.add(tagModel.uiModel())
                _state.update {
                    it.copy(isLoadingListTag = false)
                }
            }, {
                _state.update { it.copy(isLoadingListTag = false) }
            })
        }
    }

    private fun updateTag(
        name: String,
        color: String,
        id: String,
    ) {
        _state.update { it.copy(isLoadingListTag = true) }
        val item = _state.value.listTag.find { it.id == id }

        viewModelScope.launch {
            val result = updateTagCase.execute(Pair(id, TagModel(name = name, color = color)))
            result.handleResult({
                item?.let {
                    _state.value.listTag.map { it.id == id }
                    _state.update {
                        it.copy(
                            isLoadingListTag = false,
                            listTag =
                                _state.value.listTag.map {
                                    if (it.id == id) {
                                        it.copy(title = name, color = Color(color.toColor()))
                                    } else {
                                        it
                                    }
                                }.toMutableList(),
                        )
                    }
                }
            }, {
                _state.update { it.copy(isLoadingListTag = false) }
            })
        }
    }

    private fun deleteTag(id: String) {
        _state.update { it.copy(isLoadingListTag = true) }
        viewModelScope.launch {
            val result = deleteTagCase.execute(id)
            result.handleResult({ value ->
                _state.value.listTag.removeIf { it.id == id }.let { _state.value.listTag }
                _state.update { it.copy(isLoadingListTag = false) }
            }, {
                _state.update { it.copy(isLoadingListTag = false) }
            })
        }
    }

    private fun getPets() {
        _state.value = _state.value.copy(isLoadingListPet = true)
        viewModelScope.launch {
            val result = getPetListUseCase.execute(Unit)
            result.handleResult({
                _state.value = _state.value.copy(listPets = it.toPetsList(), isLoadingListPet = false)
            }, {
                _state.value = _state.value.copy(isLoadingListPet = false, hasErrorOnListPets = true)
            })
        }
    }

    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)
}
