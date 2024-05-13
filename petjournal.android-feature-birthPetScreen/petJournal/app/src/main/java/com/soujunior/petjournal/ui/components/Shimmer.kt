package com.soujunior.petjournal.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Shimmer(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing),
            RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.LightGray.copy(alpha = 0.3f),
                        Color.LightGray.copy(alpha = 0.9f),
                        Color.LightGray.copy(alpha = 0.3f)
                    ),
                    start = Offset(translateAnim.value - 1000f, translateAnim.value - 1000f),
                    end = Offset(translateAnim.value, translateAnim.value)
                )
            )
    )
}