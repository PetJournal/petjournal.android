package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputSpecies(
    textTop: String = "Title",
    textHint: String = "Hint Message",
    modifier: Modifier = Modifier.fillMaxWidth(),
    textValue: String,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
) {

    Column(modifier = modifier.padding(8.dp)) {
        Row {
            Text(
                text = textTop,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(modifier = modifier) {
            OutlinedTextField(
                modifier = modifier,
                value = textValue,
                onValueChange = { text -> onEvent(text) },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                ),
                placeholder = {
                    Text(
                        text = textHint,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                trailingIcon = {
                    if (textError != null) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = ""
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.CheckCircleOutline,
                            contentDescription = ""
                        )
                    }
                },
                isError = textError != null,
                singleLine = true,
                supportingText = {
                    textError?.forEach {
                        Text(text = it, color = Color.Red)
                    }
                },

                keyboardOptions = keyboardOptions,
            )
        }
    }
}