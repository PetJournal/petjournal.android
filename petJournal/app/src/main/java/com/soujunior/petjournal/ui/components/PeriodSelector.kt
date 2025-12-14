package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.util.SelectedPeriodType

@Composable
fun PeriodSelector(
    selected: SelectedPeriodType,
    onSelectionChanged: (SelectedPeriodType) -> Unit,
) {
    val items = listOf(SelectedPeriodType.Diária, SelectedPeriodType.Semanal, SelectedPeriodType.Mensal)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier =
                    Modifier
                        .clickable { onSelectionChanged(item) }
                        .padding(horizontal = 8.dp),
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selected == item) Color(0xFF8D4CD2) else Color.Black,
                )
                if (selected == item) {
                    TriangleIndicator()
                } else {
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun TriangleIndicator(
    // TODO: colocar na tabela de cores.
    color: Color = Color(0xFF8D4CD2),
    modifier: Modifier =
        Modifier
            .width(60.dp)
            .height(2.dp),
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val inset = width * 0.1f

        val path =
            Path().apply {
                moveTo(inset, 0f)
                lineTo(width - inset, 0f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

        drawPath(path = path, color = color)
    }
}

@Preview(showBackground = true)
@Composable
fun PeriodSelectorPreview() {
    var selectedPeriod = SelectedPeriodType.Diária
    PeriodSelector(selected = selectedPeriod) {
        selectedPeriod = it
    }
}
