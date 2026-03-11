package com.soujunior.petjournal.ui.components.task

import androidx.compose.runtime.Composable
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
    selectedAmPm: String? = null,
    onTime: (Int, Int) -> Unit = { _, _ -> },
    time: Pair<Int, Int>? = null,
    onWeekDaySelected: (String?) -> Unit = {},
    onDaySelected: (Int?) -> Unit = {},
    is24HourFormat: Boolean = false,
    onSelectedPeriod: (SelectedPeriodType) -> Unit = {},
    selectedPeriod: SelectedPeriodType = SelectedPeriodType.Daily,
) {
    PeriodSelector(
        selected = selectedPeriod,
        onSelectionChanged = onSelectedPeriod,
    )

    when (selectedPeriod) {
        SelectedPeriodType.Daily -> {
            TimePickerWithPeriodSelector(
                time = time,
                onAmPmSelector = { amPmSelector ->
                    onAmPmSelector(amPmSelector)
                },
                selectedAmPm = selectedAmPm,
                onTime = { hour, minute ->
                    onTime(hour, minute)
                },
                is24HourFormat = is24HourFormat,
            )
        }

        SelectedPeriodType.Weekly -> {
            WeeklyTaskSelector(
                onWeekDaySelected = onWeekDaySelected,
                onAmPmSelector = { amPmSelector ->
                    onAmPmSelector(amPmSelector)
                },
                selectedAmPm = selectedAmPm,
                onTime = onTime,
                is24HourFormat = is24HourFormat,
            )
        }

        SelectedPeriodType.Monthly -> {
            MonthlyTaskSelector(
                activeMonths = activeMonths,
                onDaySelected = onDaySelected,
                onAmPmSelector = { amPmSelector ->
                    onAmPmSelector(amPmSelector)
                },
                selectedAmPm = selectedAmPm,
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
