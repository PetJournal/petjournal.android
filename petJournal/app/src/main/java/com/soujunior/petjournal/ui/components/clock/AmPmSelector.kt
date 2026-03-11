package com.soujunior.petjournal.ui.components.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import java.util.Calendar

@Composable
fun AmPmSelector(
    onPeriodSelected: (String) -> Unit,
    selected: String? = null,
) {
    val timePeriodMarkerAm = stringResource(R.string.am)
    val timePeriodMarkerPm = stringResource(R.string.pm)

    val initialPeriod =
        remember {
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            if (currentHour < 12) timePeriodMarkerAm else timePeriodMarkerPm
        }

    val effectiveSelected = selected ?: initialPeriod

    LaunchedEffect(selected) {
        if (selected.isNullOrBlank()) {
            onPeriodSelected(initialPeriod)
        }
    }

    Column(
        modifier =
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp),
                )
                .height(70.dp)
                .width(31.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .width(31.dp)
                    .background(
                        if (effectiveSelected == timePeriodMarkerAm) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        },
                    )
                    .clickable {
                        onPeriodSelected(timePeriodMarkerAm)
                    },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = timePeriodMarkerAm,
                style = MaterialTheme.typography.labelLarge,
                color =
                    if (effectiveSelected == timePeriodMarkerAm) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                fontWeight = FontWeight(400),
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.primary),
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .width(31.dp)
                    .background(
                        if (effectiveSelected == timePeriodMarkerPm) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        },
                    )
                    .clickable {
                        onPeriodSelected(timePeriodMarkerPm)
                    },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = timePeriodMarkerPm,
                color =
                    if (effectiveSelected == timePeriodMarkerPm) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                style =
                    MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight(400),
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                    ),
            )
        }
    }
}
