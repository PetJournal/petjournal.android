package com.soujunior.petjournal.ui.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val paddingForm = Modifier.padding(start = 40.dp, end = 20.dp, top = 2.dp)

@Composable
fun AlertText(modifier: Modifier = paddingForm, textMessage: String){
    Text(
        text = textMessage,
        color = MaterialTheme.colors.error,
        modifier = modifier
    )
}
