package com.soujunior.data.remote.model.discord

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class DiscordWebhookPayload(
    @Json(name = "content") val content: String? = null,
    @Json(name = "embeds") val embeds: List<DiscordEmbed>? = null
)

@Keep
@JsonClass(generateAdapter = true)
data class DiscordEmbed(
    @Json(name = "title") val title: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "color") val color: Int? = null,
    @Json(name = "timestamp") val timestamp: String? = null,
    @Json(name = "fields") val fields: List<DiscordEmbedField>? = null
)

@Keep
@JsonClass(generateAdapter = true)
data class DiscordEmbedField(
    @Json(name = "name") val name: String,
    @Json(name = "value") val value: String,
    @Json(name = "inline") val inline: Boolean = false
)
