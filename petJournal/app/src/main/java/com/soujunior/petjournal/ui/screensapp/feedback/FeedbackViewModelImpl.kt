package com.soujunior.petjournal.ui.screensapp.feedback

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.feedback.SendFeedbackUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedbackViewModelImpl(
    private val sendFeedbackUseCase: SendFeedbackUseCase
) : FeedbackViewModel() {

    private val _state = MutableStateFlow(FeedbackState())
    override val state: StateFlow<FeedbackState> = _state.asStateFlow()

    override fun onEvent(event: FeedbackEvent) {
        when (event) {
            is FeedbackEvent.SubmitFeedback -> submitFeedback(event.message, event.screenContext)
            is FeedbackEvent.DismissError -> _state.update { it.copy(error = null) }
            is FeedbackEvent.ResetState -> _state.update { FeedbackState() }
        }
    }

    private fun submitFeedback(message: String, screenContext: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val result = sendFeedbackUseCase.execute(message, screenContext)
            
            result.onSuccess {
                _state.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { exception ->
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        error = exception.message ?: "Ocorreu um erro desconhecido."
                    ) 
                }
            }
        }
    }
}
