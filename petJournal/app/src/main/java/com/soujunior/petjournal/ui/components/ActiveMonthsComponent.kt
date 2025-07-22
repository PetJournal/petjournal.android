package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorCustom

@Composable
fun ActiveMonthsComponent(activeMonths: Set<String>) {
    val allMonths = listOf(
        "Jan", "Fev", "Mar", "Abr", "Mai",
        "Jun", "Jul", "Ago", "Set", "Out",
        "Nov", "Dez"
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.active_in_months),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight(400),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.scrim,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        for (i in 0 until 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (j in 0 until 5) {
                    val month = allMonths[i * 5 + j]
                    val isActive = activeMonths.contains(month)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (isActive) ColorCustom.color_background_month_active else ColorCustom.color_background_month_disabled
                            )
                            .padding(vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = month,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isActive) MaterialTheme.colorScheme.onPrimary else ColorCustom.color_border_dialog,
                            fontWeight = FontWeight(400)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 10 until 12) {
                val month = allMonths[i]
                val isActive = activeMonths.contains(month)

                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isActive) ColorCustom.color_background_month_active else ColorCustom.color_background_month_disabled
                        )
                        .padding(vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = month,
                        color = if (isActive) MaterialTheme.colorScheme.onPrimary else ColorCustom.color_border_dialog,
                        fontWeight = FontWeight(400)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun ActiveMonthsComponentPreview() {
    ActiveMonthsComponent(activeMonths = setOf("Jan", "Mar", "Mai"))
}
