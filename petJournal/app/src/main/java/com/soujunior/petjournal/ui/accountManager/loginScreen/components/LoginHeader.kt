package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo

@Composable
fun LoginHeader() {
    CreateTitleAndImageLogo(
        title = stringResource(id = R.string.access_account),
        styleTitle = MaterialTheme.typography.displayLarge,
        modifierImage = Modifier
            .size(width = 200.dp, height = 200.dp)
            .padding(top = 20.dp)
    )
    Spacer(modifier = Modifier.padding(top = 50.dp))
}