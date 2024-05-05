package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo

@Composable
fun LoginHeader() {
    CreateTitleAndImageLogo(
        title = stringResource(id = R.string.access_account),
        styleTitle = MaterialTheme.typography.displayLarge,
        modifierImage = Modifier
            .wrapContentSize(Alignment.Center)
//            .size(width = 200.dp, height = 200.dp)
//            .padding(top = 20.dp)
    )
    //Spacer(modifier = Modifier.padding(top = 50.dp))
}