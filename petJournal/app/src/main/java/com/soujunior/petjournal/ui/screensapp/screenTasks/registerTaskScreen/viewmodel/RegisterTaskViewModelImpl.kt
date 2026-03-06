package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.use_case.task.CreateTagUseCase
import com.soujunior.domain.use_case.task.DeleteTagUseCase
import com.soujunior.domain.use_case.task.GetListTagUseCase
import com.soujunior.domain.use_case.task.UpdateTagUseCase
import com.soujunior.petjournal.ui.mapper.TagModelMapper.toColor
import com.soujunior.petjournal.ui.mapper.TagModelMapper.toListSelectableButtonInfo
import com.soujunior.petjournal.ui.mapper.TagModelMapper.uiModel
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskEvent
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskState
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterTaskViewModelImpl(
    private val getListTagCase: GetListTagUseCase,
    private val createTagCase: CreateTagUseCase,
    private val updateTagCase: UpdateTagUseCase,
    private val deleteTagCase: DeleteTagUseCase,
) : RegisterTaskViewModel() {
    private val _state = MutableStateFlow(RegisterTaskState())
    override val state: MutableStateFlow<RegisterTaskState> get() = _state

    init {
        getData()
    }

    private fun getData() {
        getTags()
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
            is RegisterTaskEvent.ReloadListPet -> {}
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

    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)
}
