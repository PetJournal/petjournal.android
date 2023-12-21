package com.soujunior.petjournal.ui.appArea.pets.introRegisterPetScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.RoundedSquare

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
        Image(
            painter = painterResource(id = R.drawable.logo_purple),
            contentDescription = "Image logo",
            modifier = Modifier
                .size(calcDefault(defaultSize, 8))
        )
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
    Spacer(modifier = Modifier.padding(top = 64.dp))
}
private fun calcDefault(sizeToMultiply: Int, quantity: Int): Dp {
    return (sizeToMultiply * quantity).dp
}