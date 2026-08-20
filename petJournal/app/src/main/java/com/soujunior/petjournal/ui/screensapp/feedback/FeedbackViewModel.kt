package com.soujunior.petjournal.ui.screensapp.feedback

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class FeedbackViewModel : ViewModel() {
    abstract val state: StateFlow<FeedbackState>
    abstract fun onEvent(event: FeedbackEvent)
}

data class FeedbackState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

sealed class FeedbackEvent {
    data class SubmitFeedback(val message: String, val screenContext: String) : FeedbackEvent()
    object DismissError : FeedbackEvent()
    object ResetState : FeedbackEvent()
}
