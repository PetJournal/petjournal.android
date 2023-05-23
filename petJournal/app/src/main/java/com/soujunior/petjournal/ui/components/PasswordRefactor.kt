package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun PasswordRefactor(
    textTop: String = "Nova senha",
    textHint: String = "Digite sua nova senha",
    modifier: Modifier = Modifier.fillMaxWidth(),
    isRegister: Boolean = true,
    password: String,
    passwordErrorList: List<String>?,
    onEvent: (String) -> Unit
) {
    var inFocusPwd by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row {
            Text(
                text = textTop,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row {
            OutlinedTextField(
                value = password,
                onValueChange = { newPassword -> onEvent(newPassword) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                ),
                trailingIcon = {
                    val (icon, iconColor) = if (showPassword) {
                        Pair(
                            Icons.Filled.Visibility,
                            MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Pair(
                            Icons.Filled.VisibilityOff,
                            MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            icon,
                            contentDescription = "Modifica a visibilidade do campo senha",
                            tint = iconColor
                        )
                    }
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = textHint,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                isError = passwordErrorList != null,
                shape = Shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        inFocusPwd = if (it.hasFocus) it.hasFocus else it.hasFocus
                    }
            )
        }
    }

    if (inFocusPwd && isRegister && passwordErrorList != null) {
        passwordErrorList.forEach {
            AlertText(textMessage = it)
        }
    }
}