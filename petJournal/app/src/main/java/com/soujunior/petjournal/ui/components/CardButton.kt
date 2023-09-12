package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.util.GetScreenInch

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    image: Painter,
    cardColor: Color,
    submit: () -> Unit
) {
    val diagonal = GetScreenInch()
    val height : Int
    val width : Int

    if (diagonal > 5.6 && diagonal < 6.0) {
        height = 175
        width = 169
    } else if (diagonal < 5.5) {
        height = 175
        width = 159
    }else if (diagonal > 6.1) {
        height = 239
        width = 242
    }else {
        height = 300
        width = 300
    }
    Card(
        modifier = Modifier
            .width(height.dp)
            .height(width.dp)
            .clickable { submit() },
        RoundedCornerShape(8.dp),
        backgroundColor = cardColor
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.padding(5.dp)
        )
    }


}





