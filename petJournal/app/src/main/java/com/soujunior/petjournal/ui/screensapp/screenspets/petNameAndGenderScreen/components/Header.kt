package com.soujunior.petjournal.ui.screensapp.screenspets.petNameAndGenderScreen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Header(species: String = "Gato") {
    if (species.isNotEmpty()) {
        CreateTitleAndImageLogo(
            spaceBetween = 15.sdp,
            spaceBetweenbreadcrumbAndTitle = 10.sdp,
            title = stringResource(id = R.string.wow),
            breadcrumbEnable = true,
            breadcrumbIndex = 0,
            titleSecundary = stringResource(id = R.string.register_message, species),
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
    } else {
        CreateTitleAndImageLogo(
            spaceBetween = 20.sdp,
            spaceBetweenbreadcrumbAndTitle = 15.sdp,
            title = stringResource(id = R.string.wow),
            breadcrumbEnable = true,
            breadcrumbIndex = 0,
            titleSecundary = stringResource(id = R.string.register_message_others, species),
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHeader() {
    Header(species = "Cachorro")
}
