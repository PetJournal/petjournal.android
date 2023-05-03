package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun ComponentButton(submit: () -> Unit, modifier: Modifier, enableButton: Boolean,
                    text : String, top : Int, colorButton : ButtonColors, colorText : Color, border : BorderStroke?) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { submit() },
            enabled = enableButton,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = top.dp)
                .size(height = 50.dp, width = 200.dp),
            shape = Shapes.large,
            colors = colorButton,
            border= border,

        ) {
            Text(
                text = text,
                color= colorText,
                style = MaterialTheme.typography.button
            )
        }
    }
}