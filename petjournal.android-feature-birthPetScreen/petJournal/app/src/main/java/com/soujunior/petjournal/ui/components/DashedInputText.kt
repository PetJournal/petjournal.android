package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R

@Composable
fun DashedInputText(
    modifier : Modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth(),
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    titleText: String = "Title",
    textValue: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None){
    var inFocus by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 5.dp)
            )
        }

        Row{
            BasicTextField(
                modifier = textInputModifier
                    .background(
                        Color.Transparent
                    )
                    .fillMaxWidth().testTag("dashedInputField_test")
                    .padding(5.dp)
                    .height(40.dp)
                    .drawBehind {
                        val stroke = Stroke(
                            width = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(8.dp.toPx(), 8.dp.toPx(), 0f)
                            )
                        )
                        drawRoundRect(
                            color = if (isError) Color.Transparent else Color.Black,
                            style = stroke,
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )

                    }
                    .border(2.dp, if(isError) MaterialTheme.colorScheme.error else Color.Transparent, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
                value = textValue,
                onValueChange = {text -> onEvent(text)},
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                visualTransformation =
                if (isPassword) {
                    if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation()
                } else visualTransformation,
                keyboardOptions = keyboardOptions,
                decorationBox = {innerTextField ->
                    Row(
                        modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(
                            modifier.weight(1f)){
                            if(textValue.isEmpty()) //Placeholder
                                Text(
                                    modifier = modifier.padding(10.dp),
                                    text = placeholderText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 15.sp
                                )
                            else
                                Text(
                                    modifier = modifier.padding(10.dp),
                                    text = textValue
                                )
                        }
                        //Logica do Icone
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
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        else {
                            null
                        }
                    }
                }
            )
        }
        Row {
            if(textError != null) {
                textError.forEach {
                    AlertText(textMessage = it, modifier = Modifier.padding(10.dp))
                }
            }
            else{
                Text(
                    "*Campo Obrigatório.",
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 15.sp
                )
            }
        }
    }

}

