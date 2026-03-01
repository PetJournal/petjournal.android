package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

import androidx.compose.ui.graphics.Color
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.petjournal.ui.components.SelectableButtonInfo
import com.soujunior.petjournal.ui.components.data.TaskData

data class RegisterTaskState(
    val nameUser: String = "",
    val isLoadingUserName: Boolean = false,
    val hasErrorOnNameUser: Boolean = false,
    val listTaskData: List<TaskData> = emptyList(),
    val isLoadingListPet: Boolean = false,
    val hasErrorOnListPets: Boolean = false,
    val listPets: List<PetModel> = emptyList(),
    val hasErrorOnListTag: Boolean = false,
    val isLoadingListTag: Boolean = false,
    val listTag: List<SelectableButtonInfo> = emptyList(),
) {
    private fun TagModel.uiModel(): SelectableButtonInfo {
        return SelectableButtonInfo(
            title = this.name ?: "",
            color = Color(android.graphics.Color.parseColor(this.color)),
        )
    }

    private fun List<TagModel>.toUiModel(): List<SelectableButtonInfo> {
        return this.map { it.uiModel() }
    }

    fun convert(tags: List<TagModel>): List<SelectableButtonInfo> {
        return tags.toUiModel()
    }
}
