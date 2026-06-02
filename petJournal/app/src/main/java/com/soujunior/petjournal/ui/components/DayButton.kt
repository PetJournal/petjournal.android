package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DayButton(
    label: String,
    selectedDays: List<String>?,
    onDaySelected: (String) -> Unit,
) {
    val isSelected = selectedDays?.contains(label) == true

    val borderColor = MaterialTheme.colorScheme.primary
    val backgroundColor =
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onPrimary
        }

    Box(
        modifier =
            Modifier
                .then(
                    if (label == "Sab" || label == "Sáb") {
                        Modifier
                            .fillMaxWidth()
                            .height(28.dp)
                    } else {
                        Modifier
                            .width(42.dp)
                            .height(28.dp)
                    },
                )
                .clip(RoundedCornerShape(6.dp))
                .background(backgroundColor)
                .border(1.dp, borderColor, RoundedCornerShape(6.dp))
                .clickable {
                    onDaySelected(label)
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight(500),
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun DayButtonPreview() {
    DayButton(
        label = "Seg",
        onDaySelected = {},
        selectedDays = listOf("Seg"),
    )
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun DayButtonNotSelectedPreview() {
    DayButton(
        label = "Dom",
        onDaySelected = {},
        selectedDays = listOf("Seg"),
    )
}
