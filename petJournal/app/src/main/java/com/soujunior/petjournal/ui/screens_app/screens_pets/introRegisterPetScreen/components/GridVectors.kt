package com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.RoundedSquare
import com.soujunior.petjournal.ui.theme.ColorGrid
import ir.kaaveh.sdpcompose.sdp

@Composable
fun GridVectors() {
    val defaultSize = 11
    Row(
        modifier = Modifier.padding(vertical = 2.sdp),
        horizontalArrangement = Arrangement.spacedBy(8.sdp)
    ) {
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = calcDefault(defaultSize, 4),
            topRightRadius = defaultSize.sdp,
            bottomLeftRadius = defaultSize.sdp,
            bottomRightRadius = defaultSize.sdp,
            colorBackground = ColorGrid.purple_grid,
            image = painterResource(id = R.drawable.dog),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.sdp,
            topRightRadius = defaultSize.sdp,
            bottomLeftRadius = defaultSize.sdp,
            bottomRightRadius = defaultSize.sdp,
            colorBackground = ColorGrid.blue_grid,
            image = painterResource(id = R.drawable.cat),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.sdp,
            topRightRadius = calcDefault(defaultSize, 4),
            bottomLeftRadius = defaultSize.sdp,
            bottomRightRadius = defaultSize.sdp,
            colorBackground = ColorGrid.red_grid,
            image = painterResource(id = R.drawable.parrot),
        )

    }
    Row(
        modifier = Modifier.padding(vertical = 2.sdp),
        horizontalArrangement = Arrangement.spacedBy(8.sdp)
    ) {
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.sdp,
            topRightRadius = defaultSize.sdp,
            bottomLeftRadius = defaultSize.sdp,
            bottomRightRadius = defaultSize.sdp,
            colorBackground = ColorGrid.pink_grid,
            image = painterResource(id = R.drawable.penguim),
        )
        Image(
            painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.logo_white) else painterResource(
                id = R.drawable.logo_black
            ),
            contentDescription = "Image logo",
            modifier = Modifier
                .size(calcDefault(defaultSize, 8))
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.sdp,
            topRightRadius = defaultSize.sdp,
            bottomLeftRadius = defaultSize.sdp,
            bottomRightRadius = defaultSize.sdp,
            colorBackground = ColorGrid.pink_grid,
            image = painterResource(id = R.drawable.rat),
        )

    }

    Row(
        modifier = Modifier.padding(vertical = 2.sdp),
        horizontalArrangement = Arrangement.spacedBy(8.sdp)
    ) {
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.sdp,
            topRightRadius = defaultSize.sdp,
            bottomLeftRadius = calcDefault(defaultSize, 4),
            bottomRightRadius = defaultSize.sdp,
            colorBackground = ColorGrid.monkey_grid,
            image = painterResource(id = R.drawable.monkey),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.sdp,
            topRightRadius = defaultSize.sdp,
            bottomLeftRadius = defaultSize.sdp,
            bottomRightRadius = defaultSize.sdp,
            colorBackground = ColorGrid.shark_grid,
            image = painterResource(id = R.drawable.shark),
        )
        RoundedSquare(
            size = calcDefault(defaultSize, 8),
            topLeftRadius = defaultSize.sdp,
            topRightRadius = defaultSize.sdp,
            bottomLeftRadius = defaultSize.sdp,
            colorBackground = ColorGrid.turtle_grid,
            bottomRightRadius = calcDefault(defaultSize, 4),
            image = painterResource(id = R.drawable.turtle),
        )

    }
    Spacer(modifier = Modifier.padding(top = 13.sdp))
}

private fun calcDefault(sizeToMultiply: Int, quantity: Int): Dp {
    return (sizeToMultiply * quantity).dp
}

@Preview
@Composable
private fun GridPrev() {

    GridVectors()
}