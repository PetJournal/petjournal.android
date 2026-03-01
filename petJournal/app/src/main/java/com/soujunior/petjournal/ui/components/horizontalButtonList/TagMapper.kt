package com.soujunior.petjournal.ui.components.horizontalButtonList

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.soujunior.domain.model.response.tag.TagOption
import com.soujunior.petjournal.ui.components.bottomSheet.TaskRemoteEntity

object TagMapper {
    private val defaultOption =
        TagOption(
            id = "all_tags_option",
            label = "Todos",
            icon = Icons.Rounded.Apps,
            color = Color(0xFF6200EE),
        )

    fun toMenuOptions(tasks: List<TaskRemoteEntity>): List<TagOption> {
        val tags = tasks.map { it.tag }

        val uniqueTags = tags.distinctBy { it.id }

        val mappedOptions =
            uniqueTags.map { tagEntity ->
                TagOption(
                    id = tagEntity.id,
                    label = tagEntity.name,
                    icon = getIconByName(tagEntity.name),
                    color = parseColor(tagEntity.color),
                )
            }

        return listOf(defaultOption) + mappedOptions
    }

    private fun parseColor(hexColor: String): Color {
        return try {
            Color(android.graphics.Color.parseColor(hexColor))
        } catch (e: Exception) {
            Color.Gray
        }
    }

    private fun getIconByName(name: String): ImageVector {
        return when {
            name.contains("Vacina", ignoreCase = true) -> Icons.Default.LocalHospital
            name.contains("Banho", ignoreCase = true) -> Icons.Default.Pets
            else -> Icons.Default.Pets
        }
    }
}
