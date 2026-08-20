package com.soujunior.domain.repository.api

interface FeedbackRepository {
    suspend fun sendFeedback(message: String, screenContext: String): Result<Unit>
}
