package com.soujunior.petjournal.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.Shapes
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Button2(
    submit: () -> Unit,
    enableButton: Boolean,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    text: String = "Button",
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    isLoading: Boolean = false,
) {
    androidx.compose.material3.Button(
        onClick = { submit() },
        enabled = enableButton,
        modifier = modifier,
        border = border,
        shape = Shapes.medium,
        colors = buttonColor,
        contentPadding = PaddingValues(12.sdp),
    ) {
        if (!isLoading) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = textColor,
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(17.sdp),
                color = textColor,
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF121212,
)
@Composable
fun Button2Preview() {
    Surface {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Button2(
                submit = {},
                enableButton = true,
                text = "Botão Habilitado",
                modifier = Modifier.fillMaxWidth(),
            )

            Button2(
                submit = {},
                enableButton = false,
                text = "Botão Desabilitado",
                modifier = Modifier.fillMaxWidth(),
            )

            Button2(
                submit = {},
                enableButton = true,
                text = "Carregando",
                isLoading = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
