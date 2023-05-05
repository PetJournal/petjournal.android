package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
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
import com.soujunior.petjournal.ui.theme.DarkColors
import com.soujunior.petjournal.ui.theme.LightColors

@Composable
fun TextViewCompBody1(text : String, pStart : Int, pTop : Int, alignment : Alignment.Vertical, arrangement : Arrangement.Horizontal, withTheme : Boolean) {
    val darkTheme = isSystemInDarkTheme()
    val colors = if(withTheme){
        if (darkTheme) Color(0xFFFF4081) else Color(0xFFB90063)
    }else{Color.Unspecified}
    Row(
        verticalAlignment = alignment,
        horizontalArrangement = arrangement,
        modifier = Modifier.fillMaxWidth(),
    ) {
            Text(
                text = text,
                color = colors,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = pStart.dp, top = pTop.dp),
            )
    }
}