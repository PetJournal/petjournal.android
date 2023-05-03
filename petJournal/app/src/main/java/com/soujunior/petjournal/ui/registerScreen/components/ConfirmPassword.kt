package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun ConfirmPassword(modifier: Modifier) {
    val password by States.localPasswordState.current
    var confirmPassword by States.localConfirmPasswordState.current
    var confirmPasswordError by States.localConfirmPasswordError.current
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
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
                    contentDescription = "Modifica a visibilidade do campo confirmar senha",
                    tint = iconColor
                )
            }
        },
        singleLine = true,
        isError = confirmPasswordError,
        label = {
            Text(
                text = "Confirmar Senha",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "Confirmar Senha",
                style = MaterialTheme.typography.body1
            )
        },
        shape = Shapes.small,
        modifier = modifier
            .fillMaxWidth()
    )
    if (password.isNotBlank())
        confirmPasswordError = confirmPassword != password
}