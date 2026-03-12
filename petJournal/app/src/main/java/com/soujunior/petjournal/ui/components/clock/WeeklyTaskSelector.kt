package com.soujunior.petjournal.ui.components.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.WeekDaySelector

@Composable
fun WeeklyTaskSelector(
    onWeekDaySelected: (String) -> Unit,
    selectedDays: List<String>? = null,
    onAmPmSelector: (String?) -> Unit,
    selectedAmPm: String? = null,
    onTime: (Int, Int) -> Unit,
    time: Pair<Int, Int>? = null,
    is24HourFormat: Boolean = false,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WeekDaySelector(
            onDaySelected = { weekDaySelected ->
                onWeekDaySelected(weekDaySelected)
            },
            selectedDays = selectedDays,
        )

        if (!is24HourFormat) {
            AmPmSelector(
                onPeriodSelected = { amPmSelector -> onAmPmSelector(amPmSelector) },
                selected = selectedAmPm,
            )
        }
        Box(
            modifier =
                Modifier
                    .height(150.dp)
                    .width(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            WheelTimePicker(
                onTimeChanged = { hour, minute ->
                    onTime(hour, minute)
                },
                time = time,
                is24HourFormat = is24HourFormat,
            )
        }
    }
}
