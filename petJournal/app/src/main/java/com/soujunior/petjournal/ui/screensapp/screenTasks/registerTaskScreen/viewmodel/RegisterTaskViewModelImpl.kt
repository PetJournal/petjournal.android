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
import com.soujunior.petjournal.ui.util.SelectedPeriodType
import com.soujunior.petjournal.ui.util.TransactionType
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RegisterTaskViewModelImpl(
    private val getListTagCase: GetListTagUseCase,
    private val createTagCase: CreateTagUseCase,
    private val updateTagCase: UpdateTagUseCase,
    private val deleteTagCase: DeleteTagUseCase,
    private val getPetListUseCase: GetListPetUseCaseV2,
) : RegisterTaskViewModel() {
    private val _state = MutableStateFlow(RegisterTaskState())
    override val state: MutableStateFlow<RegisterTaskState> get() = _state

    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

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
            is RegisterTaskEvent.OnSelectTag -> {
                _state.update { it.copy(selectedTag = event.id) }
            }
            is RegisterTaskEvent.OnName -> {
                _state.update { it.copy(taskName = event.name) }
            }
            is RegisterTaskEvent.OnDescription -> {
                _state.update { it.copy(taskDescription = event.text) }
            }
            is RegisterTaskEvent.OnPetList -> {
                _state.update { state ->
                    state.copy(selectedPet = event.ids)
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
            is RegisterTaskEvent.Submit -> {
                submit()
            }
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

    private fun submit()  {
        val payload = buildTaskPayload(_state.value)

        if (payload == null) return

        viewModelScope.launch {
            println("Payload gerado com sucesso: $payload")
        }
    }

    fun formatStartAt(
        dateMillis: Long?,
        time: LocalTime?,
        isRecurrent: Boolean,
    ): String? {
        if (time == null) return null

        if (!isRecurrent && dateMillis == null) return null

        val zoneId = ZoneId.systemDefault()

        val localDate =
            if (isRecurrent || dateMillis == null) {
                LocalDate.now(zoneId)
            } else {
                Instant.ofEpochMilli(dateMillis).atZone(zoneId).toLocalDate()
            }

        val localDateTime = ZonedDateTime.of(localDate, time, zoneId)

        val utcDateTime = localDateTime.withZoneSameInstant(ZoneOffset.UTC)

        return DateTimeFormatter.ISO_INSTANT.format(utcDateTime)
    }

    /**
     * Converte o tempo selecionado no formato 12h (AM/PM) para um objeto LocalTime em 24h.
     * Retorna null se os parâmetros obrigatórios não estiverem presentes.
     */
    fun resolveTimeTo24h(
        timeSelected: Pair<Int, Int>?,
        amPmSelected: String?,
    ): LocalTime? {
        if (timeSelected == null || amPmSelected == null) return null

        val (hour12, minute) = timeSelected
        val isPm = amPmSelected.equals("PM", ignoreCase = true)

        val hour24 =
            when {
                isPm && hour12 < 12 -> hour12 + 12
                isPm && hour12 == 12 -> 12
                !isPm && hour12 == 12 -> 0
                else -> hour12
            }

        return LocalTime.of(hour24, minute)
    }

    fun buildTaskPayload(state: RegisterTaskState): TaskPayloadRequest? {
        val tagId = state.selectedTag ?: return null
        val title = state.taskName
        val description = state.taskDescription
        val note = state.observation
        val pets = state.selectedPet

        val resolvedTime =
            resolveTimeTo24h(state.timeSelected, state.amPmSelected)
                ?: return null

        val isRecurrent = state.selectedTransactionType == TransactionType.Recurrent

        val startAt =
            formatStartAt(state.dateSelected, resolvedTime, isRecurrent)
                ?: return null

        var daily = false
        var daysOfWeek = emptyList<Int>()
        var daysOfMonth = emptyList<Int>()

        when (state.selectedTransactionType) {
            TransactionType.OneOff -> {
                daily = false
                daysOfWeek = emptyList()
                daysOfMonth = emptyList()
            }
            TransactionType.Recurrent -> {
                when (state.periodType) {
                    SelectedPeriodType.Daily -> {
                        daily = true
                        daysOfWeek = emptyList()
                        daysOfMonth = emptyList()
                    }
                    SelectedPeriodType.Weekly -> {
                        daily = false
                        daysOfWeek = convertDaysOfWeek(state.selectedDaysOfWeek)
                        daysOfMonth = emptyList()
                    }
                    SelectedPeriodType.Monthly -> {
                        daily = false
                        daysOfWeek = emptyList()
                        daysOfMonth = if (state.daySelected != null) listOf(state.daySelected) else emptyList()
                    }
                }
            }
        }

        return TaskPayloadRequest(
            tagId = tagId,
            title = title,
            description = description,
            note = note,
            startAt = startAt,
            endAt = null,
            daysOfWeek = daysOfWeek,
            daysOfMonth = daysOfMonth,
            daily = daily,
            pets = pets,
        )
    }

    fun convertDaysOfWeek(days: List<String>): List<Int> {
        val dayMap =
            mapOf(
                "domingo" to 0,
                "segunda" to 1,
                "terça" to 2,
                "quarta" to 3,
                "quinta" to 4,
                "sexta" to 5,
                "sábado" to 6,
                "terca" to 2,
                "sabado" to 6,
            )

        return days.mapNotNull { day ->
            dayMap[day.lowercase().trim()]
        }.distinct().sorted()
    }
}

data class TaskPayloadRequest(
    val tagId: String,
    val title: String,
    val description: String,
    val note: String,
    val startAt: String,
    val endAt: String?,
    val daysOfWeek: List<Int>,
    val daysOfMonth: List<Int>,
    val daily: Boolean,
    val pets: List<String>,
)
