package com.soujunior.petjournal.ui.components.clock

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.theme.ColorCustom
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlin.math.abs

/**
 * Componente de seletor de tempo com comportamento de "Seleção Fixa Centralizada".
 * (Versão com alinhamento, efeito "mola/roleta" e sincronização de par corrigidos)
 *
 * @param modifier Modificador para o layout. AGORA CONTROLA A LARGURA TOTAL.
 * @param is24HourFormat Define se o formato de horas será de 24h ou 12h (Padrão: false).
 * @param itemHeight Altura de cada item na roda.
 * @param visibleItemsCount Quantidade de itens visíveis. Deve ser ímpar.
 * @param initialHour A hora inicial (Padrão: 0).
 * @param initialMinute O minuto inicial (Padrão: 0).
 * @param time Par de hora e minuto para controle e inicialização via estado externo.
 * @param textStyle Estilo do texto para os números.
 * @param focusedColor Cor do item em foco (central).
 * @param unfocusedColor Cor dos itens fora de foco.
 * @param onTimeChanged Callback que é chamado com a hora e minuto válidos sempre que a seleção é confirmada.
 */
@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    is24HourFormat: Boolean = false,
    itemHeight: Dp = 40.dp,
    visibleItemsCount: Int = 3,
    initialHour: Int = 0,
    initialMinute: Int = 0,
    time: Pair<Int, Int>? = null,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    focusedColor: Color = Color.Black,
    unfocusedColor: Color = ColorCustom.color_unfocused_wheelTimePicker,
    onTimeChanged: (hour: Int, minute: Int) -> Unit,
) {
    require(visibleItemsCount % 2 != 0) { "visibleItemsCount must be an odd number." }

    val hours = remember(is24HourFormat) { (0..if (is24HourFormat) 23 else 12).map { it.toString().padStart(2, '0') } }
    val minutes = remember { (0..59).map { it.toString().padStart(2, '0') } }

    var selectedHour by remember { mutableStateOf(time?.first ?: initialHour) }
    var selectedMinute by remember { mutableStateOf(time?.second ?: initialMinute) }

    val hourListState = rememberLazyListState()
    val minuteListState = rememberLazyListState()

    val totalHeight = itemHeight * visibleItemsCount

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        PickerColumn(
            modifier = Modifier.weight(1f),
            items = hours,
            listState = hourListState,
            initialItem = hours[selectedHour.coerceIn(0, hours.size - 1)],
            onItemSelected = {
                val value = it.toInt()
                selectedHour = value
                onTimeChanged(selectedHour, selectedMinute)
            },
            totalHeight = totalHeight,
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            textStyle = textStyle,
            focusedColor = focusedColor,
            unfocusedColor = unfocusedColor,
        )

        Box(
            modifier = Modifier.height(totalHeight),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = ":",
                style = textStyle,
                color = focusedColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        PickerColumn(
            modifier = Modifier.weight(1f),
            items = minutes,
            listState = minuteListState,
            initialItem = minutes[selectedMinute.coerceIn(0, minutes.size - 1)],
            onItemSelected = {
                val value = it.toInt()
                selectedMinute = value
                onTimeChanged(selectedHour, selectedMinute)
            },
            totalHeight = totalHeight,
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            textStyle = textStyle,
            focusedColor = focusedColor,
            unfocusedColor = unfocusedColor,
        )
    }
}

/**
 * Componente interno que representa uma única coluna rolável do seletor.
 */
@Composable
private fun PickerColumn(
    modifier: Modifier = Modifier,
    items: List<String>,
    listState: LazyListState,
    initialItem: String,
    onItemSelected: (String) -> Unit,
    totalHeight: Dp,
    itemHeight: Dp,
    visibleItemsCount: Int,
    textStyle: TextStyle,
    focusedColor: Color,
    unfocusedColor: Color,
) {
    val halfVisibleItems = visibleItemsCount / 2

    LaunchedEffect(Unit) {
        val initialDataIndex = items.indexOf(initialItem).coerceAtLeast(0)
        listState.scrollToItem(initialDataIndex)
    }

    val centralLazyColumnIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) {
                -1
            } else {
                val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
                layoutInfo.visibleItemsInfo.minByOrNull { abs((it.offset + it.size / 2) - viewportCenter) }?.index ?: -1
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { centralLazyColumnIndex }
            .distinctUntilChanged()
            .filter { it != -1 }
            .collect { index ->
                val itemDataIndex = (index - halfVisibleItems).coerceIn(0, items.size - 1)
                onItemSelected(items[itemDataIndex])
            }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .filter { !it }
            .drop(1)
            .collect {
                val layoutInfo = listState.layoutInfo
                if (layoutInfo.visibleItemsInfo.isEmpty()) return@collect

                val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
                val centralItem =
                    layoutInfo.visibleItemsInfo.minByOrNull {
                        abs((it.offset + it.size / 2) - viewportCenter)
                    } ?: return@collect

                val delta = (centralItem.offset + centralItem.size / 2) - viewportCenter
                if (delta != 0) {
                    listState.animateScrollBy(delta.toFloat())
                }
            }
    }

    Box(
        modifier = modifier.height(totalHeight),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(totalHeight),
        ) {
            items(halfVisibleItems) { Box(modifier = Modifier.height(itemHeight)) }
            items(items.size) { dataIndex ->
                val lazyColumnIndex = dataIndex + halfVisibleItems
                val isFocused = (lazyColumnIndex == centralLazyColumnIndex)
                Box(
                    modifier = Modifier.height(itemHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = items[dataIndex],
                        style =
                            textStyle.copy(
                                color = if (isFocused) focusedColor else unfocusedColor,
                                fontSize = 34.sp,
                            ),
                    )
                }
            }
            items(halfVisibleItems) { Box(modifier = Modifier.height(itemHeight)) }
        }
    }
}

/**
 * Preview para visualização no Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun TimePickerPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.height(150.dp).width(150.dp),
            contentAlignment = Alignment.Center,
        ) {
            WheelTimePicker(onTimeChanged = { h, m -> println("$h:$m") })
        }
    }
}
