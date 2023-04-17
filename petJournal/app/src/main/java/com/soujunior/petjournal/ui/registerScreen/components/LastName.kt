package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.AlertText
import com.soujunior.petjournal.ui.util.hasSpecialCharOrNumber
import com.soujunior.petjournal.ui.util.isValidLenght

@Composable
fun LastName(modifier: Modifier) {
    var lastName by StatesRegister.localLastNameState.current
    var lastNameError by StatesRegister.localLastNameError.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = lastName,
        onValueChange = { lastName = it },
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        label = {
            Text(
                text = "Sobrenome",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "eg: Oliveira",
                style = MaterialTheme.typography.body1
            )
        },
        isError = showErrorLenght || showErrorCharacter,
        shape = Shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus)
                    it.hasFocus
                else {
                    it.hasFocus
                }
            }
    )
    if (isValidLenght(lastName) && !inFocus) {
        showErrorLenght = isValidLenght(lastName)
        AlertText(textMessage = "O Sobrenome precisa ter entre 3 e 30 caracteres..")
    } else {
        showErrorLenght = false
    }
    if (hasSpecialCharOrNumber(lastName) && !inFocus) {
        showErrorCharacter = hasSpecialCharOrNumber(lastName)
        AlertText(textMessage = "Caracteres especiais não são permitidos!")
    } else {
        showErrorCharacter = false

    }
    lastNameError = isValidLenght(lastName) || hasSpecialCharOrNumber(lastName)
}
