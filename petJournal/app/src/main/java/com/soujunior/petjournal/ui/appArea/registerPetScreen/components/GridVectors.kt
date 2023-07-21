package com.soujunior.petjournal.ui.appArea.registerPetScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorGrid

@Composable
fun GridVectors() {
    val darkTheme = isSystemInDarkTheme()
    val defaultSize = 13
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = calcDefault(defaultSize, 4),
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.dog),
            material = if (darkTheme) Color(0xFF8093F1) else Color(0xFF8093F1),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.cat),
            material = if (darkTheme) Color(0xFF54C1E9) else Color(0xFF54C1E9),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = calcDefault(defaultSize, 4),
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.parrot),
            material = if (darkTheme) Color(0xFF9A0963) else Color(0xFF9A0963),
        )

    }
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.penguim),
            material = if (darkTheme) Color(0xFFFFB8EB) else Color(0xFFFFB8EB),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.logo_black),
            material = if (darkTheme) Color(0xFFFFFFFF) else Color(0xFFFFFFFF),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.rat),
            material = if (darkTheme) Color(0xFFFFB8EB) else Color(0xFFFFB8EB)
        )

    }
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = calcDefault(defaultSize, 4),
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.monkey),
            material = if (darkTheme) Color(0xFF9A0963) else Color(0xFF9A0963),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.shark),
            material = if (darkTheme) Color(0xFF54C1E9) else Color(0xFF54C1E9),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = calcDefault(defaultSize, 4),
            image = painterResource(id = R.drawable.turtle),
            material = if (darkTheme) Color(0xFF8093F1)  else Color(0xFF8093F1) ,
        )

    }

    //RoundedSquare(size = 64.dp, cornerRadius = 8.dp )

    /*val squareSize = 100.dp
    val borderRadius = 15.dp
    val cornerRadiusClip = 8.dp
    val colors = listOf(
        Color.Red, Color.Green, Color.Blue,
        Color.Yellow, Color.Cyan, Color.Magenta,
        Color.Gray, Color.Black, Color.DarkGray
    )

    val itemsPerRow = 3
    val rows = colors.chunked(itemsPerRow)
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        itemsIndexed(rows) { rowIndex, rowItems ->
            Row(Modifier.fillMaxWidth()) {
                rowItems.forEachIndexed { index, color ->
                    Box(
                        modifier = Modifier
                            .size(squareSize)
                            .background(color)
                            .border(
                                width = 0.dp,
                                color = Color.Transparent,
                                shape = RoundedCornerShape(
                                    topStart = if (index == 0) cornerRadiusClip else 0.dp,
                                    topEnd = if (index == itemsPerRow - 1) cornerRadiusClip else 0.dp,
                                    bottomStart = if (rowIndex == rows.size - 1) cornerRadiusClip else 0.dp,
                                    bottomEnd = if (rowIndex == rows.size - 1 && index == itemsPerRow - 1) cornerRadiusClip else 0.dp
                                )
                            )
                            .padding(4.dp)
                    ) {
                        // Conteúdo do quadrado (opcional)
                    }
                }
            }
        }
    }*/
    Spacer(modifier = Modifier.padding(top = 64.dp))
}
// Gustavo isso aqui talvez possa ir pra UTILS e ser reutilizado dps de alguns ajustes.
private fun calcDefault(sizeToMultiply: Int, quantity: Int): Dp {
    return (sizeToMultiply * quantity).dp
}


/*val squareSize = 100.dp
val borderRadius = 15.dp
val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta, Color.Gray, Color.Black, Color.White)

LazyColumn(modifier = Modifier.padding(16.dp)) {
    items(9) { index ->
        Box(
            modifier = Modifier
                .size(squareSize)
                .background(colors[index])
                .drawWithContent {
                    drawRoundRect(
                        color = Color.Transparent,
                        topLeft = Offset(0f, 0f),
                        size = Size(size.width, size.height),
                        cornerRadius = CornerRadius(borderRadius.toPx())
                    )
                    drawContent()
                }
                .padding(4.dp)
        ) {
            // Conteúdo do quadrado (opcional)
        }
    }
}*/

