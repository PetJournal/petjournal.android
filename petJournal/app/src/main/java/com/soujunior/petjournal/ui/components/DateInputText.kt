package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.theme.ColorGrid
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

    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.scrim,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(500),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.sdp, end = 24.sdp)
            )
        }
        Row {
            OutlinedTextField(
                modifier = textInputModifier
                    .shadow(
                        elevation = 30.dp,
                        spotColor = ColorCustom.shadow_color,
                        ambientColor = ColorCustom.shadow_color
                    )
                    .onFocusChanged {
                        inFocus = if (it.hasFocus)
                            it.hasFocus
                        else {
                            it.hasFocus
                        }
                    }
                    .height(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .fillMaxWidth()
                    .testTag("dateInputField")
                    .drawBehind {
                        val stroke = Stroke(
                            width = 2.dp.toPx(),
                        )
                        drawRoundRect(
                            color = ColorGrid.edge_not_selected,
                            style = stroke,
                            cornerRadius = CornerRadius(12.dp.toPx())
                        )

                    }
                    .clip(RoundedCornerShape(10.sdp)),
                value = textValue,
                onValueChange = { newValue ->
                    if (newValue.length <= 8) {
                        onEvent(newValue)
                    }
                },
                textStyle = TextStyle(
                    fontSize = 14.ssp,
                    lineHeight = 21.ssp,
                    fontWeight = FontWeight(300),
                    color = if (isSystemInDarkTheme()) ColorCustom.text_style_color else MaterialTheme.colorScheme.onSurface
                ),
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            fontWeight = FontWeight(300),
                            color = MaterialTheme.colorScheme.scrim,
                        ),
                        modifier = modifier.padding(bottom = 0.sdp)
                    )
                },
                maxLines = 1,
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                trailingIcon = {
                    if (isError) {
                        val iconResource = R.drawable.icone_erro
                        val contentDescription = stringResource(R.string.description_error)

                        Icon(
                            painter = painterResource(id = iconResource),
                            contentDescription = contentDescription,
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(10.sdp)
                        )
                    } else if (textValue.length >= 7) {
                        val iconResource = R.drawable.icone_verificado_ok
                        val contentDescription = stringResource(R.string.description_error)

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
        Row {
            textError?.forEach {
                AlertText(textMessage = it, modifier = Modifier.padding(10.sdp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun DateInputTextPreview() {
    DateInputText(textValue = "", onEvent = {}, textError = null, placeholderText = "13/05/1996")
}



