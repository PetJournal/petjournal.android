package com.soujunior.data.remote

import com.soujunior.data.remote.model.discord.DiscordWebhookPayload
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface DiscordWebhookService {
    @POST
    suspend fun sendFeedback(
        @Url url: String,
        @Body payload: DiscordWebhookPayload
    ): Response<Unit>
}
