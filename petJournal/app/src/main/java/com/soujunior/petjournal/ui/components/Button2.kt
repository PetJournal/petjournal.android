package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.soujunior.petjournal.ui.theme.Shapes
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun Button2(
    submit: () -> Unit,
    enableButton: Boolean,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    text: String = "Button",
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    isLoading: Boolean = false
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
            shape = Shapes.medium,
            colors = buttonColor,
            contentPadding = PaddingValues(12.sdp)

        ) {
            if (!isLoading) {
                Text(
                    text = text,
                    fontWeight = FontWeight(900),
                    fontSize = 12.ssp,
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(17.sdp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}