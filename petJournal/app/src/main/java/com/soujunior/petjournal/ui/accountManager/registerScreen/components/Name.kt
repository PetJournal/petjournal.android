package com.soujunior.petjournal.ui.accountManager.registerScreen.components

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
import com.soujunior.petjournal.ui.accountManager.registerScreen.state.StatesRegister
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.components.AlertText
import com.soujunior.petjournal.ui.util.hasSpecialCharOrNumber
import com.soujunior.petjournal.ui.util.isValidLenght

@Composable
fun Name(
    textTop: String = "Nome",
    textHint: String = "Digite seu primeiro nome",
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    var name by StatesRegister.localNameState.current
    var nameError by StatesRegister.localNameError.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }
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
                value = name,
                onValueChange = { name = it },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                ),
                placeholder = {
                    Text(
                        text = textHint,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                //colors = TextFieldDefaults.outlinedTextFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                isError = showErrorLenght || showErrorCharacter,
                shape = Shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        inFocus = if (it.hasFocus)
                            it.hasFocus
                        else {
                            it.hasFocus
                        }
                    },
            )
        }
    }


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