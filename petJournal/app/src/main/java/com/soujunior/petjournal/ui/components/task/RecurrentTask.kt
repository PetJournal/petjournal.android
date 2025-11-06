package com.soujunior.petjournal.ui.components.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.ActiveMonthsComponent
import com.soujunior.petjournal.ui.components.AmPmSelector
import com.soujunior.petjournal.ui.components.PeriodSelector
import com.soujunior.petjournal.ui.components.WeekDaySelector
import com.soujunior.petjournal.ui.components.clock.TimePickerWithPeriodSelector
import com.soujunior.petjournal.ui.components.clock.WheelTimePicker
import com.soujunior.petjournal.ui.components.data.DayPicker
import com.soujunior.petjournal.ui.util.SelectedPeriodType

@Composable
fun RecurringTask(
    activeMonths: Set<String> = setOf(),
    onAmPmSelector: (String?) -> Unit = {},
    onTime: (Int?, Int?) -> Unit = { _, _ -> },
    onWeekDaySelected: (String?) -> Unit = {},
    onDaySelected: (Int?) -> Unit = {},
) {
    var selectedPeriod by remember { mutableStateOf(SelectedPeriodType.Diária) }
    PeriodSelector(
        selected = selectedPeriod,
        onSelectionChanged = { selectedPeriod = it }
    )

    when (selectedPeriod) {
        SelectedPeriodType.Diária -> {
            TimePickerWithPeriodSelector(
                onAmPmSelector = { amPmSelector ->
                    onAmPmSelector(amPmSelector)
                },
                onTime = { hour, minute ->
                    onTime(hour, minute)
                }
            )
        }

        SelectedPeriodType.Semanal -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(top = 16.dp)
,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                WeekDaySelector(
                    onDaySelected = { weekDaySelected ->
                        onWeekDaySelected(weekDaySelected)
                    }
                )

                AmPmSelector(
                    onPeriodSelected = { amPmSelector ->
                        onAmPmSelector(amPmSelector)
                    }
                )
                Box(
                    modifier = Modifier
//                        .padding(start = 8.dp)
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

        SelectedPeriodType.Mensal -> {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .padding(top = 16.dp)
                    ,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    DayPicker(
                        onDaySelected = { daySelected ->
                            onDaySelected(daySelected)
                        }
                    )
                    AmPmSelector(
                        onPeriodSelected = { amPmSelector ->
                            onAmPmSelector(amPmSelector)
                        }
                    )
                    Box(
                        modifier = Modifier
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
                ActiveMonthsComponent(activeMonths = activeMonths)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecurringTaskPreview() {
    RecurringTask()
}