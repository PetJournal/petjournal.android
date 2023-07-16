package com.soujunior.petjournal.ui.components

import android.graphics.drawable.Icon
import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    image: Painter,
    cardColor: Color,
    submit: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(169.dp)
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





