package com.soujunior.petjournal.ui.appArea.pets.introRegisterPetScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter

@Composable
fun Header(name: String?) {
    if (name != null) {
        Text(
            text = stringResource(id = R.string.hello_name, name.capitalizeFirstLetter()),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
    }
    Spacer(modifier = Modifier.padding(12.dp))

    Text(
        text = stringResource(id = R.string.welcome_message),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    )

    Spacer(modifier = Modifier.padding(bottom = 20.dp))

}