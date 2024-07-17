package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Checkbox(
    text: String = "Texto ao lado do checkbox",
    styleText: TextStyle = MaterialTheme.typography.bodyMedium,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    radioButtonSelected: Boolean = false,
    onEvent: (Boolean) -> Unit,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(22.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(
                        1.2.dp,
                        if (radioButtonSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Checkbox(
                    checked = radioButtonSelected,
                    onCheckedChange = { onEvent(!radioButtonSelected) },
                    colors = CheckboxDefaults.colors (
                        checkedColor = Color.Transparent,
                        uncheckedColor = Color.Transparent,
                        checkmarkColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.size(10.dp)
                )
            }

            Text(
                text = text,
                style = styleText,
                color = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.Unspecified
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckboxPreview() {
    Checkbox(checked = false, onCheckedChange = null)
}
