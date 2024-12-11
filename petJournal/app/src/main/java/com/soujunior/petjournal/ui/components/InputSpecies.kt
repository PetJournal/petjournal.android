package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorCustom
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun InputSpecies(
    textTop: String = "Title",
    textHint: String = "Hint Message",
    modifier: Modifier = Modifier.fillMaxWidth(),
    textValue: String,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
) {

    Column(modifier = modifier
        .padding(5.sdp)
        .fillMaxWidth()) {
        Row {
            Text(
                text = textTop,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.ssp
            )
        }
        Row(modifier = modifier. padding(top = 5.sdp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = textValue,
                shape = ShapeDefaults.Medium,
                onValueChange = { text -> onEvent(text) },
                textStyle = TextStyle(
                    fontSize = 14.ssp,
                ),
                placeholder = {
                    Text(
                        text = textHint,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.ssp
                    )
                },
                trailingIcon = {
                    if (textError != null) {
                        Icon(
                            imageVector = Icons.Default.CheckCircleOutline,
                            contentDescription = null
                        )
                    } else if (textValue.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.icone_verificado_ok),
                            contentDescription = "",
                            tint = ColorCustom.green_confirm
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.CheckCircleOutline,
                            contentDescription = null
                        )
                    }
                },
                isError = textError != null,
                singleLine = true,
                keyboardOptions = keyboardOptions,
            )

        }
        if (textError != null) {
            textError.forEach {
                Text(text = it, color = Color.Red, fontSize = 11.ssp)
            }
        } else {
            Text(
                text = stringResource(R.string.required_field),
                color = MaterialTheme.colorScheme.outline,
                fontSize = 11.ssp
            )
        }
    }
}