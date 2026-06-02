package com.soujunior.petjournal.ui.components.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.clock.TimePickerWithPeriodSelector
import com.soujunior.petjournal.ui.components.data.CustomDatePicker

@Composable
fun OneOffTask(
    onDateSelected: (Long?) -> Unit = {},
    dateSelected: Long? = null,
    onAmPmSelector: (String?) -> Unit = {},
    selectedAmPm: String? = null,
    onTime: (Int, Int) -> Unit = { _, _ -> },
    time: Pair<Int, Int>? = null,
) {
    Column(modifier = Modifier) {
        CustomDatePicker(
            label = stringResource(R.string.date),
            value = dateSelected,
            onValueChange = {
                onDateSelected(it)
            },
            modifier = Modifier.fillMaxWidth(),
        )

        TimePickerWithPeriodSelector(
            selectedAmPm = selectedAmPm,
            onAmPmSelector = { amPmSelector ->
                onAmPmSelector(amPmSelector)
            },
            onTime = { hour, minute ->
                onTime(hour, minute)
            },
            time = time,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OneOffTaskPreview() {
    OneOffTask()
}
