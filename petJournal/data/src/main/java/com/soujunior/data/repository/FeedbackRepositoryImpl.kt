package com.soujunior.data.repository

import com.soujunior.data.remote.DiscordWebhookService
import com.soujunior.data.remote.model.discord.DiscordEmbed
import com.soujunior.data.remote.model.discord.DiscordEmbedField
import com.soujunior.data.remote.model.discord.DiscordWebhookPayload
import com.soujunior.data.BuildConfig
import com.soujunior.domain.repository.api.FeedbackRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class FeedbackRepositoryImpl(
    private val discordWebhookService: DiscordWebhookService
) : FeedbackRepository {
    
    // The Webhook URL provided by the user
    private val webhookUrl = BuildConfig.DISCORD_WEBHOOK_URL

    override suspend fun sendFeedback(message: String, screenContext: String): Result<Unit> {
        return try {
            val timestamp = getCurrentIsoTimestamp()
            
            val payload = DiscordWebhookPayload(
                embeds = listOf(
                    DiscordEmbed(
                        title = "Novo Feedback Recebido \uD83D\uDCE2",
                        color = 0x5865F2, // Discord Blurple
                        timestamp = timestamp,
                        fields = listOf(
                            DiscordEmbedField(name = "Contexto (Tela)", value = screenContext, inline = true),
                            DiscordEmbedField(name = "Mensagem", value = message, inline = false)
                        )
                    )
                )
            )

            val response = discordWebhookService.sendFeedback(webhookUrl, payload)
            
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send feedback: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getCurrentIsoTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }
}
