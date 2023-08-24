package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.appArea.registerPetScreen.components.RoundedSquare

@Composable
fun GridVectors() {
    val defaultSize = 13
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
            image = painterResource(id = R.drawable.dog),
            material = MaterialTheme.colorScheme.secondaryContainer,
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.parrot),
            material = MaterialTheme.colorScheme.secondaryContainer,
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.cat),
            material = MaterialTheme.colorScheme.secondaryContainer,
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
            image = painterResource(id = R.drawable.shark),
            material = MaterialTheme.colorScheme.secondaryContainer,
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.turtle),
            material = MaterialTheme.colorScheme.secondaryContainer
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.dp,
            topRightRadius = defaultSize.dp,
            bottomLeftRadius = defaultSize.dp,
            bottomRightRadius = defaultSize.dp,
            image = painterResource(id = R.drawable.rat),
            material = MaterialTheme.colorScheme.secondaryContainer,
        )

    }

}

//// Gustavo isso aqui talvez possa ir pra UTILS e ser reutilizado dps de alguns ajustes.
private fun calcDefault(sizeToMultiply: Int, quantity: Int): Dp {
    return (sizeToMultiply * quantity).dp
}

@Preview
@Composable
fun PreviewGid() {
    GridVectors()
}
