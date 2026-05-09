package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.soujunior.petjournal.ui.theme.PetJournalTheme

enum class ArrowDirection {
    TOP, BOTTOM, LEFT, RIGHT
}

@Composable
fun OnboardingPointer(
    text: String,
    direction: ArrowDirection,
    modifier: Modifier = Modifier,
    offset: IntOffset = IntOffset(0, 0),
    popupAlignment: Alignment? = null,
    arrowYProvider: ((totalHeight: Float) -> Float)? = null,
    onDismiss: () -> Unit = {}
) {
    val finalAlignment = popupAlignment ?: when (direction) {
        ArrowDirection.TOP -> Alignment.BottomCenter
        ArrowDirection.BOTTOM -> Alignment.TopCenter
        ArrowDirection.LEFT -> Alignment.CenterStart
        ArrowDirection.RIGHT -> Alignment.CenterEnd
    }

    Popup(
        alignment = finalAlignment,
        offset = offset,
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = false, dismissOnClickOutside = false)
    ) {
        PetJournalTheme {
            val backgroundColor = MaterialTheme.colorScheme.primaryContainer
            val textColor = MaterialTheme.colorScheme.onPrimaryContainer

            Box(modifier = modifier.padding(8.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .drawBehind {
                            val cornerRadiusPx = 8.dp.toPx()
                            val arrowSize = 10.dp.toPx()

                            val minSafeX = cornerRadiusPx + arrowSize
                            val maxSafeX = (size.width - cornerRadiusPx - arrowSize).coerceAtLeast(minSafeX)
                            val xCenter = (size.width / 2).coerceIn(minSafeX, maxSafeX)

                            val minSafeY = cornerRadiusPx + arrowSize
                            val maxSafeY = (size.height - cornerRadiusPx - arrowSize).coerceAtLeast(minSafeY)
                            val yCenter = (arrowYProvider?.invoke(size.height) ?: (size.height / 2)).coerceIn(minSafeY, maxSafeY)

                            val path = Path()

                            path.moveTo(cornerRadiusPx, 0f)

                            if (direction == ArrowDirection.TOP) {
                                path.lineTo(xCenter - arrowSize, 0f)
                                path.lineTo(xCenter, -arrowSize)
                                path.lineTo(xCenter + arrowSize, 0f)
                            }
                            path.lineTo(size.width - cornerRadiusPx, 0f)

                            path.quadraticTo(size.width, 0f, size.width, cornerRadiusPx)

                            if (direction == ArrowDirection.RIGHT) {
                                path.lineTo(size.width, yCenter - arrowSize)
                                path.lineTo(size.width + arrowSize, yCenter)
                                path.lineTo(size.width, yCenter + arrowSize)
                            }
                            path.lineTo(size.width, size.height - cornerRadiusPx)

                            path.quadraticTo(size.width, size.height, size.width - cornerRadiusPx, size.height)

                            if (direction == ArrowDirection.BOTTOM) {
                                path.lineTo(xCenter + arrowSize, size.height)
                                path.lineTo(xCenter, size.height + arrowSize)
                                path.lineTo(xCenter - arrowSize, size.height)
                            }
                            path.lineTo(cornerRadiusPx, size.height)

                            path.quadraticTo(0f, size.height, 0f, size.height - cornerRadiusPx)

                            if (direction == ArrowDirection.LEFT) {
                                path.lineTo(0f, yCenter + arrowSize)
                                path.lineTo(-arrowSize, yCenter)
                                path.lineTo(0f, yCenter - arrowSize)
                            }
                            path.lineTo(0f, cornerRadiusPx)

                            path.quadraticTo(0f, 0f, cornerRadiusPx, 0f)

                            path.close()
                            drawPath(path, backgroundColor)
                        }
                        .padding(12.dp)
                ) {
                    Text(
                        text = text,
                        color = textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Pointer - Seta para CIMA", widthDp = 300, heightDp = 150)
@Composable
fun OnboardingPointerTopPreview() {
    PetJournalTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            OnboardingPointer(
                text = "Clique aqui para gerenciar as tags!",
                direction = ArrowDirection.TOP,
                offset = IntOffset(0, 100)
            )
        }
    }
}

@Preview(showBackground = true, name = "Pointer - Seta para BAIXO", widthDp = 300, heightDp = 150)
@Composable
fun OnboardingPointerBottomPreview() {
    PetJournalTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            OnboardingPointer(
                text = "Sincronize seus dados com a nuvem",
                direction = ArrowDirection.BOTTOM,
                offset = IntOffset(0, 50)
            )
        }
    }
}

@Preview(showBackground = true, name = "Pointer - Seta para ESQUERDA", widthDp = 300, heightDp = 150)
@Composable
fun OnboardingPointerLeftPreview() {
    PetJournalTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            OnboardingPointer(
                text = "Menu de navegação lateral",
                direction = ArrowDirection.LEFT,
                offset = IntOffset(40, 50)
            )
        }
    }
}

@Preview(showBackground = true, name = "Pointer - Seta para DIREITA", widthDp = 300, heightDp = 150)
@Composable
fun OnboardingPointerRightPreview() {
    PetJournalTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            OnboardingPointer(
                text = "Opções avançadas de filtro",
                direction = ArrowDirection.RIGHT,
                offset = IntOffset(-40, 50)
            )
        }
    }
}