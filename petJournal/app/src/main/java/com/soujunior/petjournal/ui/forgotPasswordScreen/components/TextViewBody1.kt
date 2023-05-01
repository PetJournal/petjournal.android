package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextViewCompBody1(text : String, pStart : Int, pTop : Int, color : Color,
                      alignment : Alignment.Vertical, arrangement : Arrangement.Horizontal) {


    Row(

        verticalAlignment = alignment,
        horizontalArrangement = arrangement,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = pStart.dp, top = pTop.dp),
        )
    }
}