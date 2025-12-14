package com.soujunior.petjournal.ui.screensapp.screenspets.petRaceAndSizeScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Header(
    modifier: Modifier = Modifier,
    petName: String = "Bolinha",
) {
    if (petName.isNotEmpty()) {
        CreateTitleAndImageLogo(
            spaceBetween = 15.sdp,
            spaceBetweenbreadcrumbAndTitle = 10.sdp,
            title = stringResource(R.string.register_message_race, petName),
            breadcrumbEnable = true,
            breadcrumbIndex = 2,
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
    } else {
        CreateTitleAndImageLogo(
            spaceBetween = 20.sdp,
            spaceBetweenbreadcrumbAndTitle = 15.sdp,
            title = stringResource(id = R.string.register_message_race_empity),
            breadcrumbEnable = true,
            breadcrumbIndex = 2,
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
    }
    Spacer(modifier = Modifier.padding(bottom = 20.dp))
}

@Preview(showBackground = true)
@Composable
private fun PreviewHeader() {
    Header()
}
