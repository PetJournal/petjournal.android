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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

    Column(modifier = modifier.padding(top = 16.sdp)) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.scrim,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(500),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 24.sdp, end = 24.sdp)
            )
        }
        Row {
            BasicTextField(
                modifier = textInputModifier
                    .shadow(
                        elevation = 30.dp,
                        spotColor = ColorCustom.shadow_color,
                        ambientColor = ColorCustom.shadow_color
                    )
                    .height(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(2.sdp)
                    .fillMaxWidth()
                    .testTag("inputField_test")
                    .drawBehind {
                        val stroke = Stroke(
                            width = 2.dp.toPx(),
                        )
                        drawRoundRect(
                            color = if (isError) ColorCustom.error_color else ColorGrid.edge_not_selected,
                            style = stroke,
                            cornerRadius = CornerRadius(12.dp.toPx())
                        )

                    }
                    .clip(RoundedCornerShape(10.sdp)),
                value = textValue,
                onValueChange = { text -> onEvent(text) },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.ssp,
                    lineHeight = 21.ssp,
                    fontWeight = FontWeight(300),
                    color = if (isSystemInDarkTheme()) ColorCustom.shadow_color else MaterialTheme.colorScheme.onSurface
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
                                    text = placeholderText,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 21.sp,
                                        fontWeight = FontWeight(300),
                                        color = MaterialTheme.colorScheme.scrim,
                                    )
                                )
                            }
                        }
                        if (isPassword) {
                            val iconResource =
                                if (showPassword) R.drawable.eye_visibility_on else R.drawable.eye_visibility_off
                            val contentDescription =
                                if (showPassword) stringResource(R.string.hide_password) else stringResource(
                                    R.string.show_password
                                )

                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    painter = painterResource(id = iconResource),
                                    contentDescription = contentDescription,
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        } else if (isError) {
                            val iconResource = R.drawable.icone_erro
                            val contentDescription = stringResource(R.string.description_error)

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
        textError?.forEach {
            AlertText(
                textMessage = it,
                modifier = Modifier.padding(top = 6.sdp, bottom = 6.sdp, start = 10.sdp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun InputTextPreview() {
    InputText(Modifier, onEvent = {}, textValue = "")
}