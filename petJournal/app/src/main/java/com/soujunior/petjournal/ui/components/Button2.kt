package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun Button2(
    submit: () -> Unit,
    enableButton: Boolean,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    text: String = "Button",
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
                androidx.compose.material3.Button(
                    onClick = { submit() },
                    enabled = enableButton,
                    modifier = modifier,
                    border = border,
                    shape = Shapes.large,
                    colors = buttonColor
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleSmall,
                        color = textColor
                    )
                }
    }
}