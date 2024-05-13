package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
fun RadioButtonWithText(
    modifierRow: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    modifierRadioButton: Modifier = Modifier,
    textResourceId: Int = R.string.radiobutton_text,
    styleText: TextStyle = MaterialTheme.typography.bodyLarge,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    onEvent: (Boolean) -> Unit,
    radioButtonSelected: Boolean = false
) {
    val text = stringResource(id = textResourceId)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifierRow
    ) {
        RadioButton(
            selected = radioButtonSelected,
            onClick = { onEvent(!radioButtonSelected) },
            modifier = modifierRadioButton.align(alignment = Alignment.CenterVertically)
        )
        Text(
            text = text,
            modifier = modifierText.clickable(onClick = { onEvent(!radioButtonSelected) }),
            style = styleText,
            color = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
    }
}