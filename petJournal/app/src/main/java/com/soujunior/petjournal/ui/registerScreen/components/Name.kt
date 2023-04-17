package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.AlertText
import com.soujunior.petjournal.ui.util.hasSpecialCharOrNumber
import com.soujunior.petjournal.ui.util.isValidLenght

@Composable
fun Name(modifier: Modifier) {
    var name by StatesRegister.localNameState.current
    var nameError by StatesRegister.localNameError.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        label = {
            Text(
                text = "Nome",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "eg: Thayna",
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
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
            },
    )
    if (isValidLenght(name) && !inFocus) {
        showErrorLenght = isValidLenght(name)
        AlertText(textMessage = "O Nome precisa ter entre 3 e 30 caracteres..")
    } else {
        showErrorLenght = false
    }
    if (hasSpecialCharOrNumber(name) && !inFocus) {
        showErrorCharacter = hasSpecialCharOrNumber(name)
        AlertText(textMessage = "Caracteres especiais não são permitidos!")
    } else {
        showErrorCharacter = false
    }
    nameError = hasSpecialCharOrNumber(name) || isValidLenght(name)
}