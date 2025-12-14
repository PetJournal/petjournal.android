package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeekDaySelector(onDaySelected: (String) -> Unit) {
    listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
    val selectedDay = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DayButton("Dom", selectedDay, onDaySelected)
            DayButton("Seg", selectedDay, onDaySelected)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DayButton("Ter", selectedDay, onDaySelected)
            DayButton("Qua", selectedDay, onDaySelected)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DayButton("Qui", selectedDay, onDaySelected)
            DayButton("Sex", selectedDay, onDaySelected)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.width(90.dp),
        ) {
            DayButton("Sab", selectedDay, onDaySelected)
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun WeekDaySelectorPreview() {
    WeekDaySelector(
        onDaySelected = {},
    )
}
