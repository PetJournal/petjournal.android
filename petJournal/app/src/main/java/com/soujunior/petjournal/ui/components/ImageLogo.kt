package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R


@Composable
fun ImageLogo(
    modifier: Modifier = Modifier
        .size(width = 150.dp, height = 150.dp)
        .padding(top = 30.dp),
    darkMode: Boolean = isSystemInDarkTheme(),
    isBlack : Boolean = false
) {
    val image : Painter = if(!isBlack) {
        val imageLight = painterResource(id = R.drawable.logo_roxo)
        val imageDark = painterResource(id = R.drawable.logo_white)
        if (darkMode) imageDark else imageLight
    }else if (darkMode) painterResource(id = R.drawable.logo_white) else painterResource(id = R.drawable.logo_black)

    Image(
        painter = image,
        contentDescription = "Imagem logo",
        modifier = modifier
    )
}

@Preview
@Composable
fun teste(){
    ImageLogo()
}


