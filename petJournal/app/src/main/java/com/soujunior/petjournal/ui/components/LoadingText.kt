package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingText(
    titleTopBar: String = "",
    titleTopBarColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    if (isLoading) {
        Shimmer(
            modifier = modifier
                .fillMaxWidth(0.6f)
                .height(30.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
        )
    } else {
        Text(
            text = titleTopBar,
            color = titleTopBarColor,
            fontSize = 22.sp
        )
    }
}