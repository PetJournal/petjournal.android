package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.ui.graphics.Color
import com.soujunior.domain.model.PetModel
import com.soujunior.petjournal.ui.components.data.TaskData
import com.soujunior.petjournal.ui.components.horizontalButtonList.TagOption
import com.soujunior.petjournal.ui.util.Constantes.allTagsId
import com.soujunior.petjournal.ui.util.Constantes.allTagsLabel
import com.soujunior.petjournal.ui.util.Constantes.consultanceTagId
import com.soujunior.petjournal.ui.util.Constantes.consultanceTagLabel
import com.soujunior.petjournal.ui.util.Constantes.foodTagId
import com.soujunior.petjournal.ui.util.Constantes.foodTagLabel
import com.soujunior.petjournal.ui.util.Constantes.gooutTagId
import com.soujunior.petjournal.ui.util.Constantes.gooutTagLabel
import com.soujunior.petjournal.ui.util.Constantes.medicationTagId
import com.soujunior.petjournal.ui.util.Constantes.medicationTagLabel
import com.soujunior.petjournal.ui.util.Constantes.showerTagId
import com.soujunior.petjournal.ui.util.Constantes.showerTagLabel
import com.soujunior.petjournal.ui.util.Constantes.vacineTagId
import com.soujunior.petjournal.ui.util.Constantes.vacineTagLabel

data class HomeState(
    val nameUser: String = "",
    val isLoadingUserName: Boolean = false,
    val hasErrorOnNameUser: Boolean = false,
    val listTaskData: List<TaskData> = emptyList(),
    val menuItems: List<TagOption> = emptyList(),
    val isLoadingListPet: Boolean = false,
    val hasErrorOnListPets: Boolean = false,
    val listPets: List<PetModel> = emptyList(),
    val isLoadingListTag: Boolean = false,
    val hasErrorOnListTag: Boolean = false,
    val listTag: List<TagOption> =
        listOf(
            TagOption(
                id = allTagsId,
                label = allTagsLabel,
                icon = Icons.Rounded.Apps,
                color = Color(0xFF6200EE),
            ),
            TagOption(
                id = vacineTagId,
                label = vacineTagLabel,
                icon = Icons.Default.LocalHospital,
                color = Color(0xFFFF5252),
            ),
            TagOption(
                id = medicationTagId,
                label = medicationTagLabel,
                icon = Icons.Default.Healing,
                color = Color(0xFF2196F3),
            ),
            TagOption(
                id = consultanceTagId,
                label = consultanceTagLabel,
                icon = Icons.Default.DateRange,
                color = Color(0xFF009688),
            ),
            TagOption(
                id = foodTagId,
                label = foodTagLabel,
                icon = Icons.Default.Restaurant,
                color = Color(0xFFFFC107),
            ),
            TagOption(
                id = showerTagId,
                label = showerTagLabel,
                icon = Icons.Default.Spa,
                color = Color(0xFF66BB6A),
            ),
            TagOption(
                id = gooutTagId,
                label = gooutTagLabel,
                icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                color = Color(0xFF00BCD4),
            ),
        ),
)
