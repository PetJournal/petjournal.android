package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.soujunior.petjournal.R

@Composable
fun CheckboxWithText(
    modifierText: Modifier = Modifier,
    modifierCheckbox: Modifier = Modifier,
    textResourceId: Int = R.string.checkbox_text,
    styleText: TextStyle = MaterialTheme.typography.bodyLarge,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    onEvent: (Boolean) -> Unit,
    checkbox: Boolean = false
) {
    val text = stringResource(id = textResourceId)
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Checkbox(
                checked = checkbox,
                onCheckedChange = { onEvent(it) },
                modifier = modifierCheckbox.align(alignment = Alignment.CenterVertically)
            )
            Text(
                text = text,
                modifier = modifierText.clickable(onClick = { onEvent(!checkbox) }),
                style = styleText,
                color = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.Unspecified
            )
        }
    }
}
