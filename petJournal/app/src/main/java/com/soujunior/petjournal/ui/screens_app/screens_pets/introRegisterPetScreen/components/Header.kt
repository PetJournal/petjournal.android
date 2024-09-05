package com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun Header(name: String?) {
    if (!name.isNullOrEmpty()) {
        CreateTitleAndImageLogo(
            spaceBetween = 10.sdp,
            title = stringResource(id = R.string.hello_name, name.capitalizeFirstLetter()),
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.padding(10.sdp))
        Text(
            text = stringResource(id = R.string.welcome_message),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            fontSize = 12.ssp,
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else ColorCustom.dark_texts_variant,
            modifier = Modifier.padding(start = 16.sdp, end = 16.sdp)
        )
    } else {
        CreateTitleAndImageLogo(
            spaceBetween = 10.sdp,
            title = "",
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = stringResource(id = R.string.welcome_message),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 12.ssp,
            textAlign = TextAlign.Start,
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else ColorCustom.dark_texts_variant,
            modifier = Modifier.padding(start = 16.sdp, end = 16.sdp)
        )

    }
}