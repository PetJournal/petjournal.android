package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CheckboxRefactor(
    modifierText: Modifier = Modifier,
    modifierCheckbox: Modifier = Modifier,
    text: String = "Texto ao lado do checkbox",
    styleText: TextStyle = MaterialTheme.typography.bodyLarge,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    checkboxState: (Boolean) -> Unit,
    checkbox: Boolean = false
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column {
                androidx.compose.material3.Checkbox(
                    checked = checkbox,
                    onCheckedChange = { checkboxState(it) },
                    modifier = modifierCheckbox
                )
            }
            Column {
                Text(
                    text = text,
                    modifier = modifierText.clickable(onClick = { checkboxState(!checkbox) }),
                    style = styleText,
                    color = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.Unspecified
                )
            }
        }
    }
}