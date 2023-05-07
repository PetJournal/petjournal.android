package com.soujunior.petjournal.ui.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlertText(modifier: Modifier = Modifier.fillMaxWidth(), textMessage: String){
    Text(
        text = textMessage,
        color = MaterialTheme.colors.error,
        modifier = modifier
    )
}
