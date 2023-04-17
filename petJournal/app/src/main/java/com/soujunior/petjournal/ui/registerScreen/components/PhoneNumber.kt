package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.AlertText
import com.soujunior.petjournal.ui.util.mask.mobileNumberFilter

@Composable
fun PhoneNumber(modifier: Modifier) {
    var phoneNumber by StatesRegister.localPhoneNumberState.current
    var phoneNumberError by StatesRegister.localPhoneNumberError.current
    var inFocus by remember { mutableStateOf(false) }
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
        label = {
            Text(
                text = "Telefone",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "eg: 91 9 1234-4567",
                style = MaterialTheme.typography.body1
            )
        },
        isError = phoneNumberError,
        shape = Shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus) it.hasFocus
                else it.hasFocus
            },
    )

    if (!inFocus && (phoneNumber.length in 1..10)) {
        phoneNumberError = true
        AlertText(textMessage = "Complete o número de telefone!")
    } else {
        phoneNumberError = false
    }
}