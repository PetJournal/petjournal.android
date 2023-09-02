package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.Shapes

/** Componente depreciado, use o Button2*/
@Composable
fun Button(
    submit: () -> Unit,
    enableButton: Boolean,
    border: BorderStroke?,
    modifier: Modifier = Modifier,
    text: String = "Button",
    inDarkMode: Boolean,
    setSystemBarColor: Boolean,
    isLoading: Boolean = false
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
            border = border,
            shape = Shapes.large
        ) {
            if (!isLoading) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall,
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun ButtonPreview() {
    Column {
        Row {
            Button(
                submit = {},
                enableButton = true,
                border = null,
                setSystemBarColor = true,
                inDarkMode = true
            )
        }
        Row {
            Button(
                submit = {},
                enableButton = false,
                border = null,
                setSystemBarColor = false,
                inDarkMode = false
            )
        }
    }
}