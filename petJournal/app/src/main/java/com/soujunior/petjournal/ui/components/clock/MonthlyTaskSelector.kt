package com.soujunior.petjournal.ui.components.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.ActiveMonthsComponent
import com.soujunior.petjournal.ui.components.data.DayPicker

@Composable
fun MonthlyTaskSelector(
    activeMonths: Set<String>,
    onDaySelected: (Int?) -> Unit,
    onAmPmSelector: (String?) -> Unit,
    onTime: (Int?, Int?) -> Unit,
    is24HourFormat: Boolean = false,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DayPicker(
                onDaySelected = { daySelected ->
                    onDaySelected(daySelected)
                },
            )

            if (!is24HourFormat) {
                AmPmSelector(
                    onPeriodSelected = { amPmSelector ->
                        onAmPmSelector(amPmSelector)
                    },
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
                    is24HourFormat = is24HourFormat,
                )
            }
        }
        ActiveMonthsComponent(activeMonths = activeMonths)
    }
}
