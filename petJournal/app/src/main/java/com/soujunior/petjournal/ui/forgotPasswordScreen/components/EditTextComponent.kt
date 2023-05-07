package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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
fun EditTextComponent(modifier: Modifier, roundedCornerShape: RoundedCornerShape, stateP: MutableState<String>, stateE: MutableState<Boolean>, label : String, placeHolder: String) {

    var component by stateP
    var componentError by stateE
    var inFocus by remember { mutableStateOf(false) }


    OutlinedTextField(
        value = component,
        onValueChange = { newValue -> component = newValue },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        label = {
            Text(
                text = if (inFocus) "" else label,
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.body1
            )
        },
        isError = componentError,
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
    if (!isEmail(component) && component.isNotBlank() && (component.length) >= 1) {
        componentError = true
        AlertText(textMessage = "Forneça um email no formato correto.")
    } else {
        componentError = false
    }
}
