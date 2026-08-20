package com.soujunior.domain.use_case.feedback

import com.soujunior.domain.repository.api.FeedbackRepository

class SendFeedbackUseCase(
    private val repository: FeedbackRepository
) {
    suspend fun execute(message: String, screenContext: String): Result<Unit> {
        if (message.isBlank()) {
            return Result.failure(IllegalArgumentException("O feedback não pode estar vazio."))
        }
        return repository.sendFeedback(message, screenContext)
    }
}
