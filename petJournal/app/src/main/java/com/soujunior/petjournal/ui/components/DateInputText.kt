package com.soujunior.petjournal.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DateInputText(
    modifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    titleText: String = "Title",
    textValue: String,
    isError: Boolean = false,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    var inFocus by remember { mutableStateOf(false) }
    val colorBorder = MaterialTheme.colorScheme.outline
    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.ssp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.sdp, bottom = 5.sdp)
            )
        }
        Row {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = textInputModifier
                        .fillMaxWidth()
                        .padding(5.sdp)
                        .height(45.sdp)
                        .onFocusChanged {
                            inFocus = if (it.hasFocus)
                                it.hasFocus
                            else {
                                it.hasFocus
                            }
                        }
                        .drawBehind {
                            val stroke = Stroke(
                                width = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(12.dp.toPx(), 12.dp.toPx(), 0f)
                                )
                            )
                            drawRoundRect(
                                color = if (isError) Color.Transparent else colorBorder,
                                style = stroke,
                                cornerRadius = CornerRadius(10.dp.toPx())
                            )

                        }
                        .border(
                            2.dp,
                            if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                            shape = RoundedCornerShape(10.sdp)
                        )
                        .clip(RoundedCornerShape(10.sdp)),

                    value = textValue,
                    onValueChange = { newValue ->
                        Log.i("test", newValue)
                        if (newValue.length <= 8) {

                            onEvent(newValue)
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 14.ssp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    placeholder = { Text(text = placeholderText) },
                    maxLines = 1,
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    trailingIcon = {
                        if (isError) {
                            val iconResource = R.drawable.icone_erro
                            val contentDescription = "Erro"

                            Icon(
                                painter = painterResource(id = iconResource),
                                contentDescription = contentDescription,
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(10.sdp)
                            )
                        } else if (textValue.length >= 7) {
                            val iconResource = R.drawable.icone_verificado_ok
                            val contentDescription = "Erro"

                            Icon(
                                painter = painterResource(id = iconResource),
                                contentDescription = contentDescription,
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(10.sdp)
                            )
                        }
                    }
                )


            }

        }
        Row {
            if (textError != null) {
                textError.forEach {
                    AlertText(textMessage = it, modifier = Modifier.padding(10.sdp))
                }
            } else {
                Text(
                    "*Campo Obrigatório.",
                    color = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(10.sdp),
                    fontSize = 12.ssp
                )
            }
        }
    }
}



