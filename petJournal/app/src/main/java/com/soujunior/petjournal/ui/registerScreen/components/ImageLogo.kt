package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R

@Composable
fun ImageLogo(modifier: Modifier = Modifier.size(width = 150.dp, height = 150.dp).padding(top = 20.dp)) {
    val imageLight = painterResource(id = R.drawable.logo_purple)
    val imageDark = painterResource(id = R.drawable.logo_pink)
    val image = if (isSystemInDarkTheme()) imageDark else imageLight

    Image(
        painter = image,
        contentDescription = "Imagem logo",
        modifier = modifier
    )
}
