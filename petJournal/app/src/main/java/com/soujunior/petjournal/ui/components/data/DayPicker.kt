package com.soujunior.petjournal.ui.components.data

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DayPicker(
    initialDay: Int? = null,
    onDaySelected: (Int) -> Unit,
) {
    val currentDate = remember { LocalDate.now() }
    val totalDays = remember { currentDate.lengthOfMonth() }
    val days = remember { (1..totalDays).toList() }

    val startDay = initialDay ?: currentDate.dayOfMonth
    val initialIndex = (startDay - 1).coerceIn(0, totalDays - 1)

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val selectedIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return@derivedStateOf initialIndex

            val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
            layoutInfo.visibleItemsInfo.minByOrNull {
                abs(it.offset + (it.size / 2) - viewportCenter)
            }?.index ?: initialIndex
        }
    }

    LaunchedEffect(selectedIndex) {
        days.getOrNull(selectedIndex)?.let {
            onDaySelected(it)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "DIA", style = MaterialTheme.typography.bodySmall)

        Box(
            modifier =
                Modifier
                    .height(120.dp)
                    .width(60.dp),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                contentPadding = PaddingValues(vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight(),
            ) {
                itemsIndexed(days) { index, day ->
                    val isSelected = selectedIndex == index
                    Text(
                        text = day.toString(),
                        fontSize = if (isSelected) 26.sp else 20.sp,
                        color = if (isSelected) Color.Black else Color.LightGray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DayPickerPreview() {
    var selectedDay by remember { mutableStateOf(LocalDate.now().dayOfMonth) }

    DayPicker(
        initialDay = selectedDay,
        onDaySelected = { selectedDay = it },
    )
}
