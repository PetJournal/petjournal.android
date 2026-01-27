package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import com.soujunior.petjournal.ui.components.horizontalButtonList.TagOption
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeHomeViewModel : HomeScreenViewModel() {
    override var state by mutableStateOf(HomeState())

    override val validationEventChannel = Channel<ValidationEvent>()
    override val message = MutableStateFlow("Mensagem de Teste")

    override fun success(name: GuardianNameResponse) {}

    override fun getData() {
        state =
            state.copy(
                name = "Jorge Garcia",
                getNameError = false,
                menuItems =
                    listOf(
                        TagOption(
                            id = "all_tags_option",
                            label = "Todos",
                            icon = Icons.Rounded.Apps,
                            color = Color(0xFF6200EE),
                        ),
                        TagOption(
                            id = "tag_vaccine",
                            label = "Vacinas",
                            icon = Icons.Default.LocalHospital,
                            color = Color(0xFFFF5252),
                        ),
                        TagOption(
                            id = "tag_hygiene",
                            label = "Higiene",
                            icon = Icons.Default.Pets,
                            color = Color(0xFF2196F3),
                        ),
                        TagOption(
                            id = "tag_medication",
                            label = "Remédios",
                            icon = Icons.Default.Spa,
                            color = Color(0xFF4CAF50),
                        ),
                    ),
                listTaskData = TaskFakeData.sampleTasks.subList(0, 3),
            )
    }

    override fun logout() {}

    override val validationEvents = emptyFlow<ValidationEvent>()
    override val name: StateFlow<GuardianNameResponse> =
        MutableStateFlow(GuardianNameResponse("Jorge", ""))

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun failed(exception: Throwable?) {
    }
}

abstract class HomeScreenViewModel : ViewModel() {
    abstract val taskState: StateFlow<TaskState>
    abstract var state: HomeState
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val name: StateFlow<GuardianNameResponse>
    abstract val message: StateFlow<String>

    abstract fun success(name: GuardianNameResponse)

    abstract fun getData()

    abstract fun logout()

    abstract fun failed(exception: Throwable?)

    val carouselImages: List<Int> =
        listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
        )
}
