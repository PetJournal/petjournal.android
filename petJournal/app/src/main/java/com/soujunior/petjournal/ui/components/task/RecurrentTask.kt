package com.soujunior.petjournal.ui.components.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.ui.components.PeriodSelector
import com.soujunior.petjournal.ui.components.clock.MonthlyTaskSelector
import com.soujunior.petjournal.ui.components.clock.TimePickerWithPeriodSelector
import com.soujunior.petjournal.ui.components.clock.WeeklyTaskSelector
import com.soujunior.petjournal.ui.util.SelectedPeriodType

@Composable
fun RecurringTask(
    activeMonths: Set<String> = setOf(),
    onAmPmSelector: (String?) -> Unit = {},
    onTime: (Int?, Int?) -> Unit = { _, _ -> },
    onWeekDaySelected: (String?) -> Unit = {},
    onDaySelected: (Int?) -> Unit = {},
    is24HourFormat: Boolean = false,
) {
    var selectedPeriod by remember { mutableStateOf(SelectedPeriodType.Diária) }

    PeriodSelector(
        selected = selectedPeriod,
        onSelectionChanged = { selectedPeriod = it },
    )

    when (selectedPeriod) {
        SelectedPeriodType.Diária -> {
            TimePickerWithPeriodSelector(
                onAmPmSelector = { amPmSelector ->
                    onAmPmSelector(amPmSelector)
                },
                onTime = { hour, minute ->
                    onTime(hour, minute)
                },
                is24HourFormat = is24HourFormat,
            )
        }

        SelectedPeriodType.Semanal -> {
            WeeklyTaskSelector(
                onWeekDaySelected = onWeekDaySelected,
                onAmPmSelector = onAmPmSelector,
                onTime = onTime,
                is24HourFormat = is24HourFormat,
            )
        }

        SelectedPeriodType.Mensal -> {
            MonthlyTaskSelector(
                activeMonths = activeMonths,
                onDaySelected = onDaySelected,
                onAmPmSelector = onAmPmSelector,
                onTime = onTime,
                is24HourFormat = is24HourFormat,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecurringTaskPreview() {
    RecurringTask()
}
