package com.soujunior.petjournal.ui.components.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.clock.TimePickerWithPeriodSelector
import com.soujunior.petjournal.ui.components.data.CustomDatePicker

@Composable
fun OneOffTask(
    onDateSelected: (Long?) -> Unit = {},
    onAmPmSelector: (String?) -> Unit = {},
    onTime: (Int, Int) -> Unit = { _, _ -> }
) {
    var selectedTimestamp by remember { mutableStateOf<Long?>(null) }

    Column(modifier = Modifier) {
        CustomDatePicker(
            label = "Data",
            value = selectedTimestamp,
            onValueChange = {
                selectedTimestamp = it
                onDateSelected(selectedTimestamp)
            },
            modifier = Modifier.fillMaxWidth()
        )

        TimePickerWithPeriodSelector(
            onAmPmSelector = { amPmSelector ->
                onAmPmSelector(amPmSelector)
            },
            onTime = { hour, minute ->
                onTime(hour, minute)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OneOffTaskPreview() {
    OneOffTask()
}