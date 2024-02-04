package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedSquare(
    size: Dp,
    topLeftRadius: Dp,
    topRightRadius: Dp,
    bottomLeftRadius: Dp,
    bottomRightRadius: Dp,
    image: Painter,
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(
                    topStart = topLeftRadius,
                    topEnd = topRightRadius,
                    bottomStart = bottomLeftRadius,
                    bottomEnd = bottomRightRadius
                )
            )
    )
    {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
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
    color: Color = Color.Blue,
    selectedColor: Color,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = color,
                shape = RoundedCornerShape(
                    topStart = topLeftRadius,
                    topEnd = topRightRadius,
                    bottomStart = bottomLeftRadius,
                    bottomEnd = bottomRightRadius
                )
            )
            .drawBehind {
                val stroke = Stroke(
                    width = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(8.dp.toPx(), 8.dp.toPx(), 0f)
                    )
                )
                drawRoundRect(
                    color = if (isSelected) Color.Transparent else Color.Black,
                    style = stroke,
                    cornerRadius = CornerRadius(30.dp.toPx())
                )

            }
            .border(
                2.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(30.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStart = topLeftRadius,
                    topEnd = topRightRadius,
                    bottomStart = bottomLeftRadius,
                    bottomEnd = bottomRightRadius
                )
            )

            .clickable {
                onClick()
            }
    )
    {
        Column(
            modifier = modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = modifier
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = image,
                    contentDescription = "Icone de genero do animal",
                    modifier = Modifier
                        .fillMaxSize(0.70f)
                        .background(Color.Yellow)
                )

            }
            Row(
                modifier = modifier
                    .padding(top = 2.dp, bottom = 5.dp),
            ) {
                Text(
                    text = text,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = color,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}