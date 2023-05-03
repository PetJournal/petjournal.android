package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.AlertText
import com.soujunior.petjournal.ui.util.isEmail

@Composable
fun Email(modifier: Modifier = Modifier, isRegister: Boolean = true) {
    var email by States.localEmailState.current
    var emailError by States.localEmailError.current
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = email,
        onValueChange = { newEmail -> email = newEmail },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        label = {
            Text(
                text = "Email",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "eg: exemple@petjournal.com",
                style = MaterialTheme.typography.body1
            )
        },
        isError = emailError,
        shape = Shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp)
            .onFocusChanged {
                inFocus = if (it.hasFocus)
                    it.hasFocus
                else {
                    it.hasFocus
                }
            }
    )
    if (isRegister && !isEmail(email) && email.isNotBlank() && (email.length) >= 1) {
        emailError = true
        AlertText(textMessage = "Forneça um email no formato correto.")
    } else {
        emailError = false
    }
}