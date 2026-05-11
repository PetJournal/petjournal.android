package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R

@Composable
fun WeekDaySelector(
    onDaySelected: (String) -> Unit,
    selectedDays: List<String>? = null,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DayButton(
                label = stringResource(R.string.sumday),
                selectedDays = selectedDays,
                onDaySelected = onDaySelected,
            )
            DayButton(
                label = stringResource(R.string.monday),
                selectedDays = selectedDays,
                onDaySelected = onDaySelected,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DayButton(
                label = stringResource(R.string.tuesday),
                selectedDays = selectedDays,
                onDaySelected = onDaySelected,
            )
            DayButton(
                label = stringResource(R.string.wednesday),
                selectedDays = selectedDays,
                onDaySelected = onDaySelected,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DayButton(
                label = stringResource(R.string.thursday),
                selectedDays = selectedDays,
                onDaySelected = onDaySelected,
            )
            DayButton(
                label = stringResource(R.string.friday),
                selectedDays = selectedDays,
                onDaySelected = onDaySelected,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.width(90.dp),
        ) {
            DayButton(
                label = stringResource(R.string.saturday),
                selectedDays = selectedDays,
                onDaySelected = onDaySelected,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun WeekDaySelectorPreview() {
    WeekDaySelector(
        onDaySelected = {},
        selectedDays = listOf("Seg", "Qua", "Sex"),
    )
}
