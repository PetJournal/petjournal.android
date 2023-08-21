package com.soujunior.petjournal.ui.appArea.homeScreen


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class HomeScreenViewModel : ViewModel() {

    abstract var state: HomeState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(name: String)
    abstract fun failed(exception: Throwable?)

    val carouselImages: List<Int> = listOf(
        R.drawable.banner1, // Replace with actual resource IDs
        R.drawable.banner2,
        R.drawable.banner3
    )


}
