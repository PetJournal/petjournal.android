package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.ssp

@Composable
fun RoundedSquare(
    size: Dp,
    topLeftRadius: Dp,
    topRightRadius: Dp,
    bottomLeftRadius: Dp,
    bottomRightRadius: Dp,
    image: Painter,
    colorBackground: Color = Color.Transparent,
) {
    Box(
        modifier =
            Modifier
                .size(size)
                .background(
                    color = colorBackground,
                    shape =
                        RoundedCornerShape(
                            topStart = topLeftRadius,
                            topEnd = topRightRadius,
                            bottomStart = bottomLeftRadius,
                            bottomEnd = bottomRightRadius,
                        ),
                ),
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxSize(0.70f)
                    .align(Alignment.Center)
                    .background(Color.Transparent),
        )
    }
}

/**
 * Rounded Square com a adição de alguns parâmetros, utilizados nos botões de seleção de gênero
 *
 * @param isSelected  valor para verificar se o componente está selecionado.
 * @param selectedColor  valor que contém a cor que colorirá a borda do componente selecionado.
 * @param text  valor que contém o texto a ser inserido abaixo da imagem
 * */
@Composable
fun RoundedSquare(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    size: Dp,
    topLeftRadius: Dp,
    topRightRadius: Dp,
    bottomLeftRadius: Dp,
    bottomRightRadius: Dp,
    image: Painter,
    colorBackground: Color = MaterialTheme.colorScheme.onBackground,
    colorText: Color = MaterialTheme.colorScheme.onBackground,
    selectedColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(size)
                .background(
                    color = colorBackground,
                    shape =
                        RoundedCornerShape(
                            topStart = topLeftRadius,
                            topEnd = topRightRadius,
                            bottomStart = bottomLeftRadius,
                            bottomEnd = bottomRightRadius,
                        ),
                )
                .drawBehind {
                    val stroke =
                        Stroke(
                            width = 1.5.dp.toPx(),
                            pathEffect =
                                PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(8.dp.toPx(), 8.dp.toPx(), 0f),
                                ),
                        )
                    drawRoundRect(
                        color = if (isSelected) Color.Transparent else Color.Black,
                        style = stroke,
                        cornerRadius = CornerRadius(30.dp.toPx()),
                    )
                }
                .border(
                    2.dp,
                    if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(30.dp),
                )
                .clip(
                    RoundedCornerShape(
                        topStart = topLeftRadius,
                        topEnd = topRightRadius,
                        bottomStart = bottomLeftRadius,
                        bottomEnd = bottomRightRadius,
                    ),
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick,
                ),
    ) {
        Column(
            modifier = modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier =
                    modifier
                        .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = image,
                    contentDescription = stringResource(R.string.animal_gender_icon),
                    modifier =
                        Modifier
                            .fillMaxSize(0.70f),
                )
            }
            Row(
                modifier =
                    modifier
                        .padding(top = 2.dp, bottom = 5.dp),
            ) {
                Text(
                    text = text,
                    fontSize = 11.ssp,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    color = colorText,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun RoundedSquarePreview() {
    MaterialTheme {
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text("Versão Simples:")
            RoundedSquare(
                size = 100.dp,
                topLeftRadius = 20.dp,
                topRightRadius = 20.dp,
                bottomLeftRadius = 20.dp,
                bottomRightRadius = 20.dp,
                image = rememberVectorPainter(Icons.Default.Star),
                colorBackground = Color.LightGray.copy(alpha = 0.3f),
            )

            Text("Versão Interativa (Toggle):")

            var selectedItem by remember { mutableStateOf("Dog") }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                RoundedSquare(
                    text = "Cachorro",
                    isSelected = selectedItem == "Dog",
                    size = 120.dp,
                    topLeftRadius = 30.dp,
                    topRightRadius = 30.dp,
                    bottomLeftRadius = 30.dp,
                    bottomRightRadius = 30.dp,
                    image = rememberVectorPainter(Icons.Default.Pets),
                    selectedColor = MaterialTheme.colorScheme.primary,
                    onClick = { selectedItem = "Dog" },
                )

                RoundedSquare(
                    text = "Gato",
                    isSelected = selectedItem == "Cat",
                    size = 120.dp,
                    topLeftRadius = 30.dp,
                    topRightRadius = 30.dp,
                    bottomLeftRadius = 30.dp,
                    bottomRightRadius = 30.dp,
                    image = rememberVectorPainter(Icons.Default.Pets),
                    selectedColor = MaterialTheme.colorScheme.primary,
                    onClick = { selectedItem = "Cat" },
                )
            }
        }
    }
}
