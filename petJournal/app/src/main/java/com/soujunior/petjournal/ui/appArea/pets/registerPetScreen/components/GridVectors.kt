package com.soujunior.petjournal.ui.appArea.pets.registerPetScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.ImageLogo
import com.soujunior.petjournal.ui.theme.ColorGrid

@Composable
fun GridVectors() {
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
            material = MaterialTheme.colorScheme.secondaryContainer,
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.cat),
            material = MaterialTheme.colorScheme.inverseSurface,
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = calcDefault(defaultSize, 4),
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.parrot),
            material = MaterialTheme.colorScheme.onSecondaryContainer,
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
            material = MaterialTheme.colorScheme.surfaceTint,
        )
        ImageLogo(isBlack = true, modifier = Modifier.size(calcDefault(defaultSize, 8),))
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.rat),
            material = MaterialTheme.colorScheme.surfaceTint
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
            material = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.shark),
            material = MaterialTheme.colorScheme.inverseSurface,
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = calcDefault(defaultSize, 4),
            image = painterResource(id = R.drawable.turtle),
            material = MaterialTheme.colorScheme.secondaryContainer ,
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

