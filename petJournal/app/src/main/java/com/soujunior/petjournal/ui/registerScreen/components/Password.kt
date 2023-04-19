package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.AlertText
import com.soujunior.petjournal.ui.util.countCharacters

@Composable
fun Password(modifier: Modifier = Modifier, isRegister: Boolean = true) {
    var password by States.localPasswordState.current
    var passwordError by States.localPasswordError.current
    var inFocusPwd by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

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
        label = { Text(text = "Senha", fontFamily = FontFamily(Font(R.font.fredoka_regular))) },
        placeholder = {
            Text(
                text = "Senha",
                style = MaterialTheme.typography.body1
            )
        },
        isError = passwordError,
        shape = Shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp)
            .onFocusChanged {
                inFocusPwd = if (it.hasFocus) it.hasFocus else it.hasFocus
            }
    )
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