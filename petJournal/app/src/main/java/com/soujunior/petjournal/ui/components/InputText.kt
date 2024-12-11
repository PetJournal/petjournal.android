package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun InputText(
    textTop: String = "Title",
    textHint: String = "Hint Message",
    modifier: Modifier = Modifier.fillMaxWidth(),
    textValue: String,
    isPassword: Boolean = false,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var inFocus by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row {
            Text(
                text = textTop,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )
        }
        Row {
            OutlinedTextField(
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
                singleLine = true,
                visualTransformation =
                if (isPassword) {
                    if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation()
                } else visualTransformation,
                trailingIcon = {
                    if (isPassword) {
                        val iconResource =
                            if (showPassword) R.drawable.eye_visibility_on else R.drawable.eye_visibility_off
                        val contentDescription =
                            if (showPassword) "Ocultar senha" else "Mostrar senha"

                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                painter = painterResource(id = iconResource),
                                contentDescription = contentDescription,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else {
                        null
                    }
                },
                keyboardOptions = keyboardOptions,

                isError = textError != null,
                shape = Shapes.small,
                modifier = modifier
                    .onFocusChanged {
                        inFocus = if (it.hasFocus)
                            it.hasFocus
                        else {
                            it.hasFocus
                        }
                    },
            )

        }
        textError?.forEach {
            AlertText(textMessage = it)
        }
    }
}