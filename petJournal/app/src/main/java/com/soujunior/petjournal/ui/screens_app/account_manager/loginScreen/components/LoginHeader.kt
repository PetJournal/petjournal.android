package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo

@Composable
fun LoginHeader() {
    CreateTitleAndImageLogo(
        spaceBetween = 40.dp,
        title = stringResource(id = R.string.access_account),
        styleTitle = MaterialTheme.typography.displayMedium,

    )
}
@Preview(showBackground = true)
@Composable
fun LoginHeaderPrev() {
    MaterialTheme {
        LoginHeader()
    }
}
