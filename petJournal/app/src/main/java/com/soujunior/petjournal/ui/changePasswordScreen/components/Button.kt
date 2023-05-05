package com.soujunior.petjournal.ui.changePasswordScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun Button(
    submit: () -> Unit,
    enableButton: Boolean,
    modifier: Modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
        .size(height = 50.dp, width = 200.dp),
    text: String = "Button"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { submit() },
            enabled = enableButton,
            modifier = modifier,
            shape = Shapes.large
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.button
            )
        }
    }
}