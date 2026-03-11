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

@Composable
fun TimePickerWithPeriodSelector(
    onAmPmSelector: (String) -> Unit,
    selectedAmPm: String? = null,
    onTime: (Int, Int) -> Unit,
    time: Pair<Int, Int>? = null,
    is24HourFormat: Boolean = false,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!is24HourFormat) {
            AmPmSelector(
                onPeriodSelected = { periodSelected ->
                    onAmPmSelector(periodSelected)
                },
                selected = selectedAmPm,
            )
        }
        Box(
            modifier =
                Modifier
                    .padding(start = 8.dp)
                    .height(150.dp)
                    .width(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            WheelTimePicker(
                time = time,
                onTimeChanged = { hour, minute -> onTime(hour, minute) },
                is24HourFormat = is24HourFormat,
            )
        }
    }
}
