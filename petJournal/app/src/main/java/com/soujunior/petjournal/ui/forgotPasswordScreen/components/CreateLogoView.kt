package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R


@Composable
fun CreateLogoView(logo : Int, width : Int, height : Int, top : Int,
                   alignment : Alignment.Vertical, arrangement : Arrangement.Horizontal ) {

    Row(
        verticalAlignment = alignment,
        horizontalArrangement = arrangement,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = logo),
            contentDescription = "Imagem logo",
            modifier = Modifier
                .size(width = width.dp, height = height.dp)
                .padding(top = top.dp)
        )
    }

}



