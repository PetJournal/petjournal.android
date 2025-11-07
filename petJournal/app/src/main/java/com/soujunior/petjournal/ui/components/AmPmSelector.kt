package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.PetJournalTheme

@Composable
fun AmPmSelector(
    onPeriodSelected: (String) -> Unit
) {
    val selected = remember { mutableStateOf<String?>(null) }
    val timePeriodMarkerAm = stringResource(R.string.am)
    val timePeriodMarkerPm = stringResource(R.string.pm)
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFB78AF7), RoundedCornerShape(8.dp))
            .height(70.dp)
            .width(31.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .width(31.dp)
                .background(if (selected.value == timePeriodMarkerAm) Color(0xFFB78AF7) else Color.White)
                .clickable {
                    selected.value = timePeriodMarkerAm
                    onPeriodSelected(timePeriodMarkerAm)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = timePeriodMarkerAm,
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFF2E2E2E),
                fontWeight = FontWeight(400),
                lineHeight = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        Divider(
            color = Color(0xFFB78AF7),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .width(31.dp)
                .background(if (selected.value == timePeriodMarkerPm) Color(0xFFB78AF7) else Color.White)
                .clickable {
                    selected.value = timePeriodMarkerPm
                    onPeriodSelected(timePeriodMarkerPm)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = timePeriodMarkerPm,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color(0xFF2E2E2E),
                    fontWeight = FontWeight(400),
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun AmPmSelectorPreview() {
    PetJournalTheme{
        AmPmSelector(
            onPeriodSelected = {},
        )
    }
}
