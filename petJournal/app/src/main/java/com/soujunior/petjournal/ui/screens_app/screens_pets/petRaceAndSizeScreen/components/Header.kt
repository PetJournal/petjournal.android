package com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Breadcrumb
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.theme.ColorCustom
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Composable
fun Header(modifier: Modifier = Modifier, petName: String = "Bolinha") {
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