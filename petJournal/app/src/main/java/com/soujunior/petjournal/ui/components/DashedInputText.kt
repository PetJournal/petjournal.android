package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DashedInputText(
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
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.sdp, bottom = 5.sdp, top = 15.sdp)
            )
        }

        Row {
            BasicTextField(
                modifier = textInputModifier
                    .fillMaxWidth()
                    .testTag("dashedInputField_test")
                    .padding(5.sdp)
                    .height(40.sdp)
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
                onValueChange = { text -> onEvent(text) },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = if (isSystemInDarkTheme()) Color.Black else MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                visualTransformation =
                if (isPassword) {
                    if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
                } else visualTransformation,
                keyboardOptions = keyboardOptions,
                decorationBox = {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onPrimary)
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
//todo:                                    style = MaterialTheme.typography.labelLarge,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.outline,
//todo:                                    A fonte é definida pelo Style, nao faz sentido colocar isso diretamente no código
//                                    fontSize = 14.ssp
                                )
                            }
                            it()
                        }
                        if (isPassword) {
                            val iconResource =
                                if (showPassword) R.drawable.eye_visibility_on else R.drawable.eye_visibility_off
                            val contentDescription =
                                if (showPassword) "O" +
                                        stringResource(R.string.hide_psswd) else stringResource(R.string.show_psswd)
//todo:                            Strings Devem ser colocadas no arquivo de strings!
//                                        "cultar senha" else "Mostrar senha"

                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    painter = painterResource(id = iconResource),
                                    contentDescription = contentDescription,
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        } else if (isError) {
                            val iconResource = R.drawable.icone_erro
                            val contentDescription = stringResource(R.string.error)
//todo:                          Strings Devem ser colocadas no arquivo de strings!
//                          val contentDescription = "Erro"

                            Icon(
                                painter = painterResource(id = iconResource),
                                contentDescription = contentDescription,
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(10.sdp)
                            )
                        }
                    }
                }
            )
        }
    }
//todo:    Esse componente precisa ser um Column
//    Row(
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Start,
    ) {
        if (textError != null) {
            textError.forEach {
                AlertText(
                    textMessage = it,
                    modifier = Modifier.padding(
                        top = 4.sdp,
                        bottom = 6.sdp,
                        start = 10.sdp
                    )
                )
            }
        } else {
            Text(
//                Strings Devem ser colocadas no arquivo de strings!
//                "*Campo Obrigatório.",
                stringResource(R.string.required_field),
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(10.sdp),
//                Fontes sao definidas pelo Typography!
//                fontSize = 11.ssp
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun DashedInputTextPreview() {
    DashedInputText(Modifier, onEvent = {}, textValue = "")
}