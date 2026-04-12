package com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LoginHeader() {
    CreateTitleAndImageLogo(
        spaceBetween = 40.sdp,
        titleSecundary = stringResource(id = R.string.access_account),
        styleTitle = MaterialTheme.typography.headlineLarge,
    )
}

@Preview(showBackground = true)
@Composable
fun LoginHeaderPrev() {
    MaterialTheme {
        LoginHeader()
    }
}
