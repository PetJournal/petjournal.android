package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.util.Constants

@Composable
fun RoundedSquare(
    size: Dp,
    topLeftRadius: Dp,
    topRightRadius: Dp,
    bottomLeftRadius: Dp,
    bottomRightRadius: Dp,
    image: Painter,
    material: Color
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = material,
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
                .align(Alignment.Center),
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
    material: Color,
    selectedColor: Color
) {

    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = material,
                shape = RoundedCornerShape(
                    topStart = topLeftRadius,
                    topEnd = topRightRadius,
                    bottomStart = bottomLeftRadius,
                    bottomEnd = bottomRightRadius
                )
            )
            .dashedBorder(
                shape = RoundedCornerShape(30.dp),
                isError = false,
                isSelected = isSelected,
                strokeWidth = 1.5.dp,
                selectedColor = selectedColor
            )
    )
    {
        Column(
            modifier = modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally){

            Row(modifier = modifier
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = image,
                    contentDescription = "Icone de genero do animal",
                    modifier = Modifier
                        .fillMaxSize(0.70f)
                )

            }
            Row (
                modifier = modifier
                    .padding(top = 2.dp, bottom = 5.dp),
                ){
                Text(
                    text = text,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}