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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.components.mask.mobileNumberFilter

@Composable
fun PhoneNumber(
    textTop: String = "Telefone",
    modifier: Modifier = Modifier
) {
    var phoneNumber by StatesRegister.localPhoneNumberState.current
    var phoneNumberError by StatesRegister.localPhoneNumberError.current
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
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= 11) phoneNumber = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = { mobileNumberFilter(it) },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                ),
                placeholder = {
                    Text(
                        text = "eg: 91 9 1234-4567",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                isError = phoneNumberError,
                shape = Shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        inFocus = if (it.hasFocus) it.hasFocus
                        else it.hasFocus
                    },
            )
        }
    }


    if (phoneNumber.length in 1..10) {
        phoneNumberError = true
        AlertText(textMessage = "Complete o número de telefone!")
    } else {
        phoneNumberError = false
    }
}