package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R

@Composable
fun InputText(
    textTop: String = "Title",
    textHint: String = "Hint Message",
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
    textValue: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var inFocus by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }


    Column(modifier = modifier) {
        Row {
            Text(
                text = textTop,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 5.dp)
            )
        }
        Row {
            OutlinedTextField(
                value = textValue,
                onValueChange = { text -> onEvent(text) },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.outline
                ),
                placeholder = {
                    Text(
                        text = textHint,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 10.sp
                    )
                },
                singleLine = true,
                visualTransformation =
                if (isPassword) {
                    if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation()
                } else visualTransformation,
                trailingIcon = {
                    if (isPassword) {
                        val iconResource =
                            if (showPassword) R.drawable.eye_visibility_on else R.drawable.eye_visibility_off
                        val contentDescription =
                            if (showPassword) "Ocultar senha" else "Mostrar senha"

                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                painter = painterResource(id = iconResource),
                                contentDescription = contentDescription,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else if(isError) {
                        val iconResource = R.drawable.icone_erro
                        val contentDescription = "Erro"

                        Icon(
                            painter = painterResource(id = iconResource),
                            contentDescription = contentDescription,
                            tint = Color.Unspecified
                        )
                    }
                    else {
                        null
                    }
                },
                keyboardOptions = keyboardOptions,

                isError = textError != null,
                colors= OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = modifier
                    .height(40.dp)
                    .dashedBorder(
                        shape = RoundedCornerShape(10.dp),
                        isError = isError,
                        isSelected = false
                    )
                    .onFocusChanged {
                        inFocus = if (it.hasFocus)
                            it.hasFocus
                        else {
                            it.hasFocus
                        }
                    },

            )


        }
        Row {
            Text(
                "*Campo Obrigatório.",
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 15.sp)
        }
    }
    textError?.forEach {
        AlertText(textMessage = it)
    }
}

@Composable
private fun CustomInputText(
    modifier : Modifier = Modifier,
    leadingIcon : (@Composable ()-> Unit)? = null,
    trailingIcon : (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
){
    var text by rememberSaveable {
        mutableStateOf("")
    }

    BasicTextField(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surface
            )
            .fillMaxWidth()
            .height(40.dp)
            .dashedBorder(
                shape = RoundedCornerShape(35),
                isError = false,
                isSelected = false
            )
        ,
        value = text,
        onValueChange = {text = it},
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.outline
        ),
        decorationBox = {innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ){
                if(leadingIcon != null)
                    leadingIcon()
                Box(
                    modifier.weight(1f)){
                        if(text.isEmpty())
                            Text(
                                modifier = modifier.padding(5.dp),
                                text = placeholderText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 15.sp
                        )
                }
            }
        }

    )

}

@Preview
@Composable
private fun PreviewIT(){
    CustomInputText(
        leadingIcon = null,
        trailingIcon = null,
        placeholderText = "Digite aqui..."
    )

//    InputText(
//        textHint = "Teste de Hint",
//        textValue = "Teste",
//        onEvent = {},
//        isError = false )
}

