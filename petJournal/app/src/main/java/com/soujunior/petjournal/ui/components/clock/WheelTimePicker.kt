package com.soujunior.petjournal.ui.components.clock


import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlin.math.abs

/**
 * Componente de seletor de tempo com comportamento de "Seleção Fixa Centralizada".
 * (Versão com alinhamento e efeito "mola/roleta" corrigidos)
 *
 * @param modifier Modificador para o layout. AGORA CONTROLA A LARGURA TOTAL.
 * @param itemHeight Altura de cada item na roda.
 * @param visibleItemsCount Quantidade de itens visíveis. Deve ser ímpar.
 * @param initialHour A hora inicial (Padrão: 0).
 * @param initialMinute O minuto inicial (Padrão: 0).
 * @param textStyle Estilo do texto para os números.
 * @param focusedColor Cor do item em foco (central).
 * @param unfocusedColor Cor dos itens fora de foco.
 * @param onTimeChanged Callback que é chamado com a hora e minuto válidos sempre que a seleção é confirmada.
 */
@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    itemHeight: Dp = 40.dp,
    visibleItemsCount: Int = 3,
    initialHour: Int = 0,
    initialMinute: Int = 0,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    focusedColor: Color = Color.Black,
    unfocusedColor: Color = Color.Gray,
    onTimeChanged: (hour: Int, minute: Int) -> Unit
) {
    require(visibleItemsCount % 2 != 0) { "visibleItemsCount must be an odd number." }

    val hours = remember { (0..23).map { it.toString().padStart(2, '0') } }
    val minutes = remember { (0..59).map { it.toString().padStart(2, '0') } }

    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }

    val hourListState = rememberLazyListState()
    val minuteListState = rememberLazyListState()

    LaunchedEffect(selectedHour, selectedMinute) {
        onTimeChanged(selectedHour, selectedMinute)
    }

    val totalHeight = itemHeight * visibleItemsCount

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PickerColumn(
            modifier = Modifier.weight(1f),
            items = hours,
            listState = hourListState,
            initialItem = hours[initialHour],
            onItemSelected = { selectedHour = it.toInt() },
            totalHeight = totalHeight,
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            textStyle = textStyle,
            focusedColor = focusedColor,
            unfocusedColor = unfocusedColor
        )

        Box(
            modifier = Modifier.height(totalHeight),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ":",
                style = textStyle,
                color = focusedColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }


        PickerColumn(
            modifier = Modifier.weight(1f),
            items = minutes,
            listState = minuteListState,
            initialItem = minutes[initialMinute],
            onItemSelected = { selectedMinute = it.toInt() },
            totalHeight = totalHeight,
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            textStyle = textStyle,
            focusedColor = focusedColor,
            unfocusedColor = unfocusedColor
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
    unfocusedColor: Color
) {
    val halfVisibleItems = visibleItemsCount / 2
    val initialDataIndex = items.indexOf(initialItem).coerceAtLeast(0)
    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        val initialLazyColumnIndex = initialDataIndex + halfVisibleItems
        val offsetPx = with(density) { ((totalHeight - itemHeight) / 2).roundToPx() }
        listState.scrollToItem(initialLazyColumnIndex, offsetPx)
        onItemSelected(items[initialDataIndex])
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
        snapshotFlow { listState.isScrollInProgress }
            .filter { !it }
            .drop(1)
            .collect {
                val layoutInfo = listState.layoutInfo
                if (layoutInfo.visibleItemsInfo.isEmpty()) return@collect

                val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
                val centralItem = layoutInfo.visibleItemsInfo.minByOrNull {
                    abs((it.offset + it.size / 2) - viewportCenter)
                } ?: return@collect

                val itemDataIndex = (centralItem.index - halfVisibleItems).coerceIn(0, items.size - 1)
                val delta = (centralItem.offset + centralItem.size / 2) - viewportCenter

                listState.animateScrollBy(delta.toFloat())

                onItemSelected(items[itemDataIndex])
            }
    }

    Box(
        modifier = modifier.height(totalHeight),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(totalHeight)
        ) {
            items(halfVisibleItems) { Box(modifier = Modifier.height(itemHeight)) }

            items(items.size) { dataIndex ->
                val lazyColumnIndex = dataIndex + halfVisibleItems
                val isFocused = (lazyColumnIndex == centralLazyColumnIndex)
                val scale by animateFloatAsState(targetValue = if (isFocused) 1.2f else 1.0f, label = "scale")
                val alpha by animateFloatAsState(targetValue = if (isFocused) 1.0f else 0.5f, label = "alpha")

                Box(
                    modifier = Modifier.height(itemHeight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[dataIndex],
                        style = textStyle.copy(
                            color = if (isFocused) focusedColor else unfocusedColor,
                            fontSize = textStyle.fontSize * scale
                        ),
                        modifier = Modifier.alpha(alpha)
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
            modifier = Modifier
                .height(150.dp)
                .width(100.dp),
            contentAlignment = Alignment.Center
        ) {
            WheelTimePicker(
                onTimeChanged = { hour, minute ->
                    println("Hora selecionada: $hour:$minute")
                }
            )
        }
    }
}

//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.gestures.animateScrollBy
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyListState
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.runtime.snapshotFlow
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.flow.drop
//import kotlinx.coroutines.flow.filter
//import kotlin.math.abs
//
///**
// * Componente de seletor de tempo com comportamento de "Seleção Fixa Centralizada".
// * (Versão com alinhamento e efeito "mola/roleta" corrigidos)
// *
// * @param modifier Modificador para o layout.
// * @param itemHeight Altura de cada item na roda.
// * @param visibleItemsCount Quantidade de itens visíveis. Deve ser ímpar.
// * @param initialHour A hora inicial (Padrão: 0).
// * @param initialMinute O minuto inicial (Padrão: 0).
// * @param textStyle Estilo do texto para os números.
// * @param focusedColor Cor do item em foco (central).
// * @param unfocusedColor Cor dos itens fora de foco.
// * @param onTimeChanged Callback que é chamado com a hora e minuto válidos sempre que a seleção é confirmada.
// */
//@Composable
//fun WheelTimePicker(
//    modifier: Modifier = Modifier,
//    itemHeight: Dp = 40.dp,
//    visibleItemsCount: Int = 3,
//    initialHour: Int = 0,
//    initialMinute: Int = 0,
//    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
//    focusedColor: Color = Color.Black,
//    unfocusedColor: Color = Color.Gray,
//    onTimeChanged: (hour: Int, minute: Int) -> Unit
//) {
//    require(visibleItemsCount % 2 != 0) { "visibleItemsCount must be an odd number." }
//
//    val hours = remember { (0..23).map { it.toString().padStart(2, '0') } }
//    val minutes = remember { (0..59).map { it.toString().padStart(2, '0') } }
//
//    var selectedHour by remember { mutableStateOf(initialHour) }
//    var selectedMinute by remember { mutableStateOf(initialMinute) }
//
//    val hourListState = rememberLazyListState()
//    val minuteListState = rememberLazyListState()
//
//    LaunchedEffect(selectedHour, selectedMinute) {
//        onTimeChanged(selectedHour, selectedMinute)
//    }
//
//    val totalHeight = itemHeight * visibleItemsCount
//
//    Row(
//        modifier = modifier,
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        PickerColumn(
//            items = hours,
//            listState = hourListState,
//            initialItem = hours[initialHour],
//            onItemSelected = { selectedHour = it.toInt() },
//            totalHeight = totalHeight,
//            itemHeight = itemHeight,
//            visibleItemsCount = visibleItemsCount,
//            textStyle = textStyle,
//            focusedColor = focusedColor,
//            unfocusedColor = unfocusedColor
//        )
//
//        //Envolve o Text em um Box para garantir alinhamento vertical perfeito.
//        Box(
//            modifier = Modifier.height(totalHeight),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = ":",
//                style = textStyle,
//                color = focusedColor,
//                fontSize = 32.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//
//        PickerColumn(
//            items = minutes,
//            listState = minuteListState,
//            initialItem = minutes[initialMinute],
//            onItemSelected = { selectedMinute = it.toInt() },
//            totalHeight = totalHeight,
//            itemHeight = itemHeight,
//            visibleItemsCount = visibleItemsCount,
//            textStyle = textStyle,
//            focusedColor = focusedColor,
//            unfocusedColor = unfocusedColor
//        )
//    }
//}
//
///**
// * Componente interno que representa uma única coluna rolável do seletor.
// */
//@Composable
//private fun PickerColumn(
//    items: List<String>,
//    listState: LazyListState,
//    initialItem: String,
//    onItemSelected: (String) -> Unit,
//    totalHeight: Dp,
//    itemHeight: Dp,
//    visibleItemsCount: Int,
//    textStyle: TextStyle,
//    focusedColor: Color,
//    unfocusedColor: Color
//) {
//    val halfVisibleItems = visibleItemsCount / 2
//    val initialDataIndex = items.indexOf(initialItem).coerceAtLeast(0)
//    val density = LocalDensity.current
//
//    // Efeito para posicionar o item inicial corretamente no centro na primeira vez.
//    LaunchedEffect(Unit) {
//        val initialLazyColumnIndex = initialDataIndex + halfVisibleItems
//        val offsetPx = with(density) { ((totalHeight - itemHeight) / 2).roundToPx() }
//        listState.scrollToItem(initialLazyColumnIndex, offsetPx)
//        onItemSelected(items[initialDataIndex])
//    }
//
//    // Lógica robusta para detetar o item central baseada na posição em pixéis.
//    val centralLazyColumnIndex by remember {
//        derivedStateOf {
//            val layoutInfo = listState.layoutInfo
//            if (layoutInfo.visibleItemsInfo.isEmpty()) {
//                -1
//            } else {
//                val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
//                layoutInfo.visibleItemsInfo.minByOrNull { abs((it.offset + it.size / 2) - viewportCenter) }?.index ?: -1
//            }
//        }
//    }
//
//    //Lógica de "snap" (mola/roleta) que puxa o item mais próximo para o centro.
//    LaunchedEffect(listState) {
//        snapshotFlow { listState.isScrollInProgress }
//            .filter { !it }
//            .drop(1)
//            .collect {
//                val layoutInfo = listState.layoutInfo
//                if (layoutInfo.visibleItemsInfo.isEmpty()) return@collect
//
//                val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
//                val centralItem = layoutInfo.visibleItemsInfo.minByOrNull {
//                    abs((it.offset + it.size / 2) - viewportCenter)
//                } ?: return@collect
//
//                val itemDataIndex = (centralItem.index - halfVisibleItems).coerceIn(0, items.size - 1)
//                val delta = (centralItem.offset + centralItem.size / 2) - viewportCenter
//
//                // Anima a rolagem pela distância exata em pixéis para centrar o item.
//                listState.animateScrollBy(delta.toFloat())
//
//                // Após a animação, atualiza o estado com o item selecionado correto.
//                onItemSelected(items[itemDataIndex])
//            }
//    }
//
//    Box(
//        modifier = Modifier.height(totalHeight).width(itemHeight * 1.5f),
//        contentAlignment = Alignment.Center
//    ) {
//        LazyColumn(
//            state = listState,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.height(totalHeight)
//        ) {
//            items(halfVisibleItems) { Box(modifier = Modifier.height(itemHeight)) }
//
//            items(items.size) { dataIndex ->
//                val lazyColumnIndex = dataIndex + halfVisibleItems
//                val isFocused = (lazyColumnIndex == centralLazyColumnIndex)
//                val scale by animateFloatAsState(targetValue = if (isFocused) 1.2f else 1.0f, label = "scale")
//                val alpha by animateFloatAsState(targetValue = if (isFocused) 1.0f else 0.5f, label = "alpha")
//
//                Box(
//                    modifier = Modifier.height(itemHeight),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = items[dataIndex],
//                        style = textStyle.copy(
//                            color = if (isFocused) focusedColor else unfocusedColor,
//                            fontSize = textStyle.fontSize * scale
//                        ),
//                        modifier = Modifier.alpha(alpha)
//                    )
//                }
//            }
//
//            items(halfVisibleItems) { Box(modifier = Modifier.height(itemHeight)) }
//        }
//    }
//}
//
//@Preview(showBackground = true, widthDp = 360)
//@Composable
//fun TimePickerPreview() {
//    MaterialTheme {
//        Box(
//            modifier = Modifier
//                .height(120.dp)
//                .fillMaxWidth(),
//            contentAlignment = Alignment.Center
//        ) {
//            WheelTimePicker(
//                onTimeChanged = { hour, minute ->
//                    println("Hora selecionada: $hour:$minute")
//                }
//            )
//        }
//    }
//}