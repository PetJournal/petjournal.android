package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.soujunior.domain.model.request.taskModels.TaskDTO
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.repository.PreferenceRepository
import com.soujunior.domain.repository.SyncStateRepository
import com.soujunior.domain.use_case.base.DataResult
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV2
import com.soujunior.domain.use_case.tag.CreateTagUseCase
import com.soujunior.domain.use_case.tag.DeleteTagUseCase
import com.soujunior.domain.use_case.tag.GetListTagUseCase
import com.soujunior.domain.use_case.tag.UpdateTagUseCase
import com.soujunior.domain.use_case.task.CreateTaskParams
import com.soujunior.domain.use_case.task.CreateTaskUseCase
import com.soujunior.domain.use_case.task.GetLocalTasksByPeriodUseCase
import com.soujunior.domain.use_case.util.TaskDateCalculator
import com.soujunior.domain.use_case.util.TaskPeriod
import com.soujunior.petjournal.infrastructure.worker.SyncTasksWorker
import com.soujunior.petjournal.ui.mapper.Mapper.toColor
import com.soujunior.petjournal.ui.mapper.Mapper.toListSelectableButtonInfo
import com.soujunior.petjournal.ui.mapper.Mapper.toPetsList
import com.soujunior.petjournal.ui.mapper.Mapper.uiModel
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskEvent
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskState
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.TagOnboardingStep
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
    private val createTaskUseCase: CreateTaskUseCase,
    private val preferenceRepository: PreferenceRepository,
    private val getLocalTasksByPeriodUseCase: GetLocalTasksByPeriodUseCase,
    private val syncStateRepository: SyncStateRepository,
    private val context: Context,
) : RegisterTaskViewModel() {
    private val _state = MutableStateFlow(RegisterTaskState())
    override val state: MutableStateFlow<RegisterTaskState> get() = _state

    private val alwaysShowOnboarding = false

    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    init {
        getData()
        checkTagOnboarding()
    }

    private fun checkTagOnboarding() {
        viewModelScope.launch {
            if (alwaysShowOnboarding) {
                _state.update { it.copy(tagOnboardingStep = TagOnboardingStep.INTRO) }
                return@launch
            }

            val isTagTutorialCompleted = preferenceRepository.isTagTutorialCompleted()

            val result =
                getLocalTasksByPeriodUseCase.execute(
                    GetLocalTasksByPeriodUseCase.Input(
                        startAt = LocalDate.now().minusMonths(1).atStartOfDay().toString(),
                        endAt = LocalDate.now().plusDays(1).atStartOfDay().toString(),
                        considerTime = false,
                    ),
                )

            val hasTasks =
                when (result) {
                    is DataResult.Success -> result.data.data.isNotEmpty()
                    else -> false
                }

            if (!isTagTutorialCompleted && !hasTasks) {
                _state.update { it.copy(tagOnboardingStep = TagOnboardingStep.INTRO) }
            }
        }
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
            is RegisterTaskEvent.OnSendToApiChanged -> {
                _state.update {
                    it.copy(sendToApi = event.value)
                }
            }
            is RegisterTaskEvent.OnNextTagOnboardingStep -> {
                val nextStep =
                    when (state.value.tagOnboardingStep) {
                        TagOnboardingStep.IDLE -> TagOnboardingStep.IDLE
                        TagOnboardingStep.INTRO -> TagOnboardingStep.MANAGE_LIST
                        TagOnboardingStep.MANAGE_LIST -> TagOnboardingStep.CREATE_FORM
                        TagOnboardingStep.CREATE_FORM -> {
                            viewModelScope.launch { preferenceRepository.setTagTutorialCompleted(true) }
                            TagOnboardingStep.COMPLETED
                        }
                        TagOnboardingStep.COMPLETED -> TagOnboardingStep.COMPLETED
                    }
                _state.update { it.copy(tagOnboardingStep = nextStep) }
            }
            is RegisterTaskEvent.OnDismissTagOnboarding -> {
                viewModelScope.launch { preferenceRepository.setTagTutorialCompleted(true) }
                _state.update { it.copy(tagOnboardingStep = TagOnboardingStep.IDLE) }
            }
            is RegisterTaskEvent.Submit -> {
                if (isFormComplete()) submit()
            }

            is RegisterTaskEvent.OnCardDialogError -> {
                _state.update {
                    it.copy(showDialogError = false)
                }
            }

            is RegisterTaskEvent.OnCardDialogAddNewTask -> {
                _state.update {
                    it.copy(
                        showDialogSuccess = false,
                        selectedTag = null,
                        taskName = "",
                        taskDescription = "",
                        observation = "",
                        selectedPet = emptyList(),
                        selectedDaysOfWeek = emptyList(),
                        sendToApi = true,
                    )
                }
            }
        }
    }

    override fun isFormComplete(): Boolean {
        val errorMessage: MutableList<String> = mutableListOf()
        var message = ""

        val tagId = state.value.selectedTag
        if (tagId == null) errorMessage.add("- Selecione uma tag")

        val title = state.value.taskName
        if (title.isBlank()) {
            errorMessage.add("- Insira um titulo")
        } else if (hasNumberAndSymbolInTheMiddle(title)) {
            errorMessage.add("- O título não pode ter números ou simbolos no meio")
        }

        val description = state.value.taskDescription
        if (description.isBlank()) errorMessage.add("- Insira uma descrição")
        val note = state.value.observation
        if (note.isBlank()) errorMessage.add("- Insira uma observação")
        val pets = state.value.selectedPet
        if (pets.isEmpty()) errorMessage.add("- Selecione pelo menos um pet")

        val transactionType = state.value.selectedTransactionType
        val hour = state.value.timeSelected
        val ampm = state.value.amPmSelected
        val daysOfWeek = state.value.selectedDaysOfWeek

        val date = state.value.dateSelected

        when (transactionType) {
            TransactionType.Recurrent -> {
                when (state.value.periodType) {
                    SelectedPeriodType.Weekly -> {
                        if (hour == null) errorMessage.add("- Selecione uma hora")
                        if (ampm == null) errorMessage.add("- Selecione um AM/PM")
                        if (daysOfWeek.isEmpty()) errorMessage.add("- Selecione pelo menos um dia da semana")
                    }
                    SelectedPeriodType.Daily -> {
                    }
                    SelectedPeriodType.Monthly -> {
                    }
                }
            }
            TransactionType.OneOff -> {
            }
        }
        if (errorMessage.isNotEmpty()) {
            errorMessage.map { error ->
                message = "$message $error \n"
            }

            state.update {
                it.copy(
                    showDialogError = true,
                    cardDialogMessage = message,
                )
            }
        }

        return errorMessage.isEmpty()
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
            val result = getListTagCase.execute(false)
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

    private fun submit() {
        val payload = buildTaskPayload(_state.value)

        if (payload == null) {
            return
        }

        val params = CreateTaskParams(payload, _state.value.sendToApi)

        viewModelScope.launch {
            taskState.value = TaskState.Loading
            val result = createTaskUseCase.execute(value = params)

            result.handleResult(
                { response ->
                    taskState.value = TaskState.Idle
                    if (_state.value.sendToApi) {
                        val workRequest =
                            OneTimeWorkRequestBuilder<SyncTasksWorker>()
                                .addTag("sync_after_create")
                                .build()
                        WorkManager.getInstance(context).enqueue(workRequest)
                        viewModelScope.launch {
                            syncStateRepository.invalidateTasksCache()
                        }
                    }

                    state.update {
                        it.copy(
                            showDialogSuccess = true,
                            cardDialogMessage = "Tarefa criada com sucesso!",
                        )
                    }
                },
                { error ->
                    taskState.value = TaskState.Idle
                    state.update {
                        it.copy(
                            showDialogError = true,
                            cardDialogMessage = error?.message.toString(),
                        )
                    }
                },
            )
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
                Instant.ofEpochMilli(dateMillis).atZone(ZoneOffset.UTC).toLocalDate()
            }

        val localDateTime = ZonedDateTime.of(localDate, time, zoneId)
        val utcDateTime = localDateTime.withZoneSameInstant(ZoneOffset.UTC)

        return utcDateTime.format(DateTimeFormatter.ISO_INSTANT)
    }

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

    fun buildTaskPayload(state: RegisterTaskState): TaskDTO? {
        val tagId = state.selectedTag ?: return null
        val title = state.taskName
        val description = state.taskDescription
        val note = state.observation
        val pets = state.selectedPet

        val resolvedTime =
            resolveTimeTo24h(state.timeSelected, state.amPmSelected)
                ?: return null

        val isRecurrent = state.selectedTransactionType == TransactionType.Recurrent

        val startAt = formatStartAt(state.dateSelected, resolvedTime, isRecurrent) ?: return null

        var daysOfWeek = convertDaysOfWeek(state.selectedDaysOfWeek)
        var daily = false
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
        val endAt =
            if (isRecurrent) {
                val taskPeriod =
                    when (state.periodType) {
                        SelectedPeriodType.Daily -> TaskPeriod.DAILY
                        SelectedPeriodType.Weekly -> TaskPeriod.WEEKLY
                        SelectedPeriodType.Monthly -> TaskPeriod.MONTHLY
                    }
                TaskDateCalculator.calculateDefaultEndAt(
                    startAtIso = startAt,
                    period = taskPeriod,
                    daysOfWeek = daysOfWeek,
                    dayOfMonth = state.daySelected,
                )
            } else {
                null
            }

        return TaskDTO(
            tagId = tagId,
            title = title,
            description = description,
            note = note,
            startAt = startAt,
            endAt = endAt,
            daysOfWeek = daysOfWeek,
            pets = pets,
        )
    }

    fun convertDaysOfWeek(days: List<String>): List<Int> {
        val dayMap =
            mapOf(
                "dom" to 0,
                "seg" to 1,
                "ter" to 2,
                "qua" to 3,
                "qui" to 4,
                "sex" to 5,
                "sab" to 6,
            )
        return days.mapNotNull { day ->
            dayMap[day.lowercase().trim()]
        }.distinct().sorted()
    }

    fun hasNumberAndSymbolInTheMiddle(text: String): Boolean {
        if (text.length < 3) return false

        val middleText = text.substring(1, text.lastIndex)

        val hasNumber = middleText.any { it.isDigit() }
        val hasSymbol = middleText.any { !it.isLetterOrDigit() && !it.isWhitespace() }

        return hasNumber && hasSymbol
    }
}
