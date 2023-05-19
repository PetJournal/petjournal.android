package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.isEmail

@Composable
fun Email(
    textTop: String = "Email",
    modifier: Modifier = Modifier.fillMaxWidth(),
    isRegister: Boolean = true
) {
    var email by States.localEmailState.current
    var emailError by States.localEmailError.current
    var inFocus by remember { mutableStateOf(false) }

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
                value = email,
                onValueChange = { newEmail -> email = newEmail },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                visualTransformation = VisualTransformation.None,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                ),
                placeholder = {
                    Text(
                        text = "eg: exemple@petjournal.com",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                isError = emailError,
                shape = Shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        inFocus = if (it.hasFocus)
                            it.hasFocus
                        else {
                            it.hasFocus
                        }
                    }
            )
        }
    }


    if (isRegister && !isEmail(email) && email.isNotBlank() && (email.length) >= 1) {
        emailError = true
        AlertText(textMessage = "Forneça um email no formato correto.")
    } else {
        emailError = false
    }
}