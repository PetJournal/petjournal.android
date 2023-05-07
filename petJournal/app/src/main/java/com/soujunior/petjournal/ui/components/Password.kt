package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.AlertText
import com.soujunior.petjournal.ui.util.countCharacters

@Composable
fun Password(
    textTop: String = "Nova senha",
    textHint: String = "Digite sua nova senha",
    modifier: Modifier = Modifier.fillMaxWidth(),
    isRegister: Boolean = true
) {
    var password by States.localPasswordState.current
    var passwordError by States.localPasswordError.current
    var inFocusPwd by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row {
            Text(
                text = textTop,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row {
            OutlinedTextField(
                value = password,
                onValueChange = { newPassword -> password = newPassword },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                ),
                trailingIcon = {
                    val (icon, iconColor) = if (showPassword) {
                        Pair(
                            Icons.Filled.Visibility,
                            MaterialTheme.colors.primary
                        )
                    } else {
                        Pair(
                            Icons.Filled.VisibilityOff,
                            MaterialTheme.colors.primary
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
                        style = MaterialTheme.typography.body1
                    )
                },
                isError = passwordError,
                shape = Shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        inFocusPwd = if (it.hasFocus) it.hasFocus else it.hasFocus
                    }
            )
        }
    }

    if (inFocusPwd && isRegister) {
        val listItens = countCharacters(password)
        var count = 0
        if (listItens[0] < 2) AlertText(textMessage = "Pelo menos duas letras Maiusculas (ex: F, G, ...)") else count++
        if (listItens[1] < 2) AlertText(textMessage = "Pelo menos duas letras Minusculas (ex: f, g, ...)") else count++
        if (listItens[2] < 2) AlertText(textMessage = "Pelo menos dois Simbolos (ex: %, &, ...)") else count++
        if (listItens[3] < 2) AlertText(textMessage = "Pelo menos dois Numeros (ex: 2, 5, ...)") else count++
        passwordError = count != 4
    }
}