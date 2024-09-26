package com.soujunior.petjournal.ui.screens_app.screen_home.homeScreen


import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeHomeViewModel : HomeScreenViewModel() {
    override var state: HomeState
        get() = HomeState()
        set(value) {}
    override val validationEventChannel = Channel<ValidationEvent>()
    override val message = MutableStateFlow("Mensagem de Teste")
    override fun success(name: GuardianNameResponse) {}

    override fun getData() {}

    override fun logout() {}

    override val validationEvents = emptyFlow<ValidationEvent>()
    override val name: StateFlow<GuardianNameResponse> =
        MutableStateFlow(GuardianNameResponse("Jorge", ""))


    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun failed(exception: Throwable?) { /* No-op */
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
    val carouselImages: List<Int> = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3
    )
}
