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
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun EmailRefactor(
    textTop: String = "Email",
    modifier: Modifier = Modifier.fillMaxWidth(),
    isRegister: Boolean = true,
    email: String,
    emailError: List<String>?,
    onEvent: (String) -> Unit,
) {
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
                onValueChange = { text -> onEvent(text) },
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
                isError = emailError != null,
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


    if (isRegister && emailError != null) {
        emailError.forEach {
            AlertText(textMessage = it)
        }
    }
}