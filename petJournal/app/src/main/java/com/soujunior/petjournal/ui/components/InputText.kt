package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorCustom
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

/**
 * Composable that displays a text input field with support for masking, password input, and error display.
 *
 * @param modifier Default modifier to style the component. (Optional)
 * @param textInputModifier Modifier specifically for styling the text input field. (Optional)
 * @param placeholderText Text displayed when the field is empty. Default is "Placeholder". (Optional)
 * @param titleText Title displayed above the input field. Default is "Title". (Optional)
 * @param textValue Current value of the input field. (Required)
 * @param isPassword Indicates whether the input field is a password field (hides the entered text). Default is `false`. (Optional)
 * @param isError Indicates whether the field should be displayed with an error state. Default is `false`. (Optional)
 * @param textError List of error messages to be displayed below the field (if any errors exist). Default is `null`. (Optional)
 * @param onEvent Callback triggered whenever the input field value changes. (Required)
 * @param hasAMask Indicates whether the field has a mask applied to the entered value. Default is `false`. (Optional)
 * @param keyboardOptions Keyboard settings, such as input type (text, number, etc.). Default is `KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)`. (Optional)
 * @param visualTransformation Visual transformation of the entered text (e.g., password masking). Default is `VisualTransformation.None`. (Optional)
 */

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    titleText: String = "Title",
    textValue: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    hasAMask: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var showPassword by remember { mutableStateOf(false) }
    val colorBorder = MaterialTheme.colorScheme.outline

    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.ssp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.sdp, bottom = 5.sdp, top = 15.sdp)
            )
        }
        Row {
            BasicTextField(
                modifier = textInputModifier
                    .fillMaxWidth()
                    .testTag("inputField_test")
                    .padding(5.sdp)
                    .height(45.sdp)
                    .drawBehind {
                        val stroke = Stroke(
                            width = 2.dp.toPx(),
                        )
                        drawRoundRect(
                            color = if (isError) ColorCustom.error_color else colorBorder,
                            style = stroke,
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )

                    }
                    .clip(RoundedCornerShape(10.sdp)),
                value = textValue,
                onValueChange = { text -> onEvent(text) },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 12.ssp,
                    color = if (isSystemInDarkTheme()) Color.Black else MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                visualTransformation =
                if (isPassword) {
                    if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation()
                } else visualTransformation,
                keyboardOptions = keyboardOptions,
                decorationBox = {
                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(start = 14.sdp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            if (textValue.isEmpty() && !hasAMask) {
                                Text(
                                    modifier = Modifier,
                                    text = placeholderText,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.outline,
                                    fontSize = 14.ssp
                                )
                            }
                        }
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
                        } else if (isError) {
                            val iconResource = R.drawable.icone_erro
                            val contentDescription = "Erro"

                            Icon(
                                painter = painterResource(id = iconResource),
                                contentDescription = contentDescription,
                                tint = ColorCustom.error_color,
                                modifier = Modifier.padding(10.sdp)
                            )
                        }
                    }
                }
            )

        }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        if (textError != null) {
            textError.forEach {
                AlertText(
                    textMessage = it,
                    modifier = Modifier.padding(top = 6.sdp, bottom = 6.sdp, start = 10.sdp)
                )
            }
        } else {
//            Text(
//                text = stringResource(id = R.string.required_field),
//                color = MaterialTheme.colorScheme.outline,
//                modifier = Modifier.padding(10.sdp),
//                fontSize = 11.ssp
//            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun InputTextPreview() {
    InputText(Modifier, onEvent = {}, textValue = "")
}