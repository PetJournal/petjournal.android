package com.soujunior.petjournal.ui.appArea.homeScreen


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class HomeScreenViewModel : ViewModel() {
    abstract val taskState: StateFlow<TaskState>
    abstract var state: HomeState
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val name : StateFlow<GuardianNameResponse>
    abstract val message : StateFlow<String>
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
