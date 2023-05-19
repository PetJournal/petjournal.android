package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.ui.states.States

@Composable
fun Checkbox(
    modifierText: Modifier = Modifier,
    modifierCheckbox: Modifier = Modifier,
    text: String = "Texto ao lado do checkbox",
    styleText: TextStyle = MaterialTheme.typography.bodyLarge,
    isDarkMode: Boolean = isSystemInDarkTheme()
) {
    val checked = States.checked.current
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column {
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = { checked.value = it },
                    modifier = modifierCheckbox
                )
            }
            Column {
                Text(
                    text = text,
                    modifier = modifierText.clickable(onClick = { checked.value = !checked.value }),
                    style = styleText,
                    color = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
fun CheckboxPreview() {
    Checkbox()
}
