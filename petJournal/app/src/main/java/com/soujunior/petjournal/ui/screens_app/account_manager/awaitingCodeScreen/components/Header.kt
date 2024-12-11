package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.HeaderImageLogoImagePasswordAndTitle
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Header() {
    HeaderImageLogoImagePasswordAndTitle(
        title = stringResource(R.string.txt_we_just_sent_a_code_to_your_email),
        spaceBetween = 10.sdp,
        subText = stringResource(R.string.txt_enter_the_6_digit_verification_code_sent_to_your_email_in_the_field_below),
        textAlign = TextAlign.Center,
        styleTitle = MaterialTheme.typography.bodyMedium
    )

}