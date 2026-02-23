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
import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import com.soujunior.petjournal.ui.components.horizontalButtonList.TagOption
import com.soujunior.petjournal.ui.states.TaskState
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
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeHomeViewModel : HomeScreenViewModel() {
    override val state = MutableStateFlow(HomeState())

    override val validationEventChannel = Channel<ValidationEvent>()
    override val message = MutableStateFlow("Mensagem de Teste")

    override fun success(name: GuardianNameResponse) {}

    override fun getGuardianName() {
        state.value =
            state.value.copy(
                nameUser = "Jorge Garcia",
                hasErrorOnNameUser = false,
                menuItems =
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
                listTaskData = TaskFakeData.sampleTasks.subList(0, 3),
            )
    }

    override fun onEvent(event: HomeEvent) {
        TODO("Not yet implemented")
    }

    override fun logout() {}

    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun failed(exception: Throwable?) {
    }
}

sealed class HomeEvent {
    object ReloadListPet : HomeEvent()
}

abstract class HomeScreenViewModel : ViewModel() {
    abstract val taskState: StateFlow<TaskState>
    abstract val state: StateFlow<HomeState>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent> get() = validationEventChannel.receiveAsFlow()
    abstract val message: StateFlow<String>

    abstract fun success(name: GuardianNameResponse)

    abstract fun getGuardianName()

    abstract fun onEvent(event: HomeEvent)

    abstract fun logout()

    abstract fun failed(exception: Throwable?)

    val carouselImages: List<Int> =
        listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
        )
}
