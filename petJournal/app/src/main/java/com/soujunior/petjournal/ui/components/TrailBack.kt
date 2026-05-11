package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.sdp

@Composable
fun TrailBack() {
    Image(
        painter = painterResource(R.drawable.rastro_back),
        contentDescription = null,
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .offset(y = 80.sdp)
                .graphicsLayer(alpha = 0.5f),
    )
}
