package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.HeaderImageLogoImagePasswordAndTitle

@Composable
fun Header() {
    HeaderImageLogoImagePasswordAndTitle(
        title = stringResource(R.string.txt_we_just_sent_a_code_to_your_email),
        spaceBetween = 10.dp,
        subText = stringResource(R.string.txt_enter_the_6_digit_verification_code_sent_to_your_email_in_the_field_below),
        textAlign = TextAlign.Center,
        styleTitle = MaterialTheme.typography.bodyMedium
    )

}