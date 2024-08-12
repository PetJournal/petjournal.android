package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MenuRow(items: List<Pair<Int, Color>>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEachIndexed { index, pair ->
            CardButton(
                image = painterResource(id = pair.first),
                cardColor = pair.second,
                submit = { },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
            if (index < items.size - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}