package com.soujunior.petjournal.ui.util

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AlertText(padding: Modifier, textMessage: String){
    Text(
        text = textMessage,
        color = MaterialTheme.colors.error,
        modifier = padding
    )
}
