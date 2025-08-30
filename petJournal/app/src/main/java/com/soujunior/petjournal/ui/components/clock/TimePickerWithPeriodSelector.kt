package com.soujunior.petjournal.ui.components.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.AmPmSelector

@Composable
fun TimePickerWithPeriodSelector(
    onAmPmSelector: (String) -> Unit,
    onTime: (Int, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AmPmSelector(
            onPeriodSelected = { periodSelected ->
                onAmPmSelector(periodSelected)
            }
        )
        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .height(150.dp)
                .width(100.dp),
            contentAlignment = Alignment.Center
        ) {
            WheelTimePicker(
                onTimeChanged = { hour, minute ->
                    onTime(hour, minute)
                }
            )
        }
    }
}