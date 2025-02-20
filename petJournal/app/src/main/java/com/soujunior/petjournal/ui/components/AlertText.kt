package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AlertText(modifier: Modifier = Modifier.fillMaxWidth(), textMessage: String?) {
    if (textMessage != null)
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = textMessage,
            modifier = modifier,
            color = MaterialTheme.colorScheme.error
        )
}
