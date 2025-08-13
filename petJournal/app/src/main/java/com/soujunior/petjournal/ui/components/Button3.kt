package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun Button3(
    submit: () -> Unit,
    enableButton: Boolean,
    modifier: Modifier = Modifier,
    text: String = "Button",
    textSize: TextUnit = 12.ssp,
    contentPaddingValues : Dp = 12.sdp,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
    textColor: Color = MaterialTheme.colorScheme.primary,
    isLoading: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(top = 20.sdp, bottom = 20.sdp)
            .fillMaxWidth()
    ) {
        androidx.compose.material3.Button(
            onClick = { submit() },
            enabled = enableButton,
            modifier = modifier
                .width(120.sdp)
                .shadow(
                    elevation = 15.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
            ,
            border = BorderStroke(
                width = 1.sdp,
                color = Color(0xFF959EA6)
            ),

            shape = RoundedCornerShape(size = 50.dp),
            colors = buttonColor,
            contentPadding = PaddingValues(contentPaddingValues)
        ) {
            if (!isLoading) {
                Text(
                    text = text,
                    fontWeight = FontWeight.W500,
                    fontSize = textSize,
                    style = MaterialTheme.typography.headlineLarge,
                    color = textColor
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(17.sdp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun Button3Preview() {
    Button3(submit = { /*TODO*/ }, enableButton = true, text = "Salvar")
}
