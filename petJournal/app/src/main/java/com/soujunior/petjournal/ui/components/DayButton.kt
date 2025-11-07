 package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DayButton(
    label: String,
    selectedDay: MutableState<String?>,
    onDaySelected: (String) -> Unit
) {
    val isSelected = selectedDay.value == label
    val borderColor = Color(0xFFB78AF7)
    val backgroundColor = if (isSelected) Color(0xFFB78AF7) else Color.White

    Box(
        modifier = Modifier
            .then(
                if (label == "Sab") Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                else Modifier
                    .width(42.dp)
                    .height(28.dp)
            )
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .clickable {
                selectedDay.value = label
                onDaySelected(label)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            lineHeight = 20.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF2E2E2E),
            textAlign = TextAlign.Center,
            letterSpacing = 0.1.sp,
        )
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun DayButtonPreview() {
    DayButton(
        label = "Seg",
        onDaySelected = {},
        selectedDay = remember { mutableStateOf("Seg") }
    )
}
