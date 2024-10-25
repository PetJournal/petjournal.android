package com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.components

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
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter
import ir.kaaveh.sdpcompose.sdp


@Composable
fun Header(name: String?) {

    if (!name.isNullOrEmpty()) {
        CreateTitleAndImageLogo(
            spaceBetween = 25.sdp,
            title = stringResource(id = R.string.hello_choice_specie, name.capitalizeFirstLetter()),
            styleTitle = MaterialTheme.typography.headlineLarge,
        )

    } else {
        CreateTitleAndImageLogo(
            spaceBetween = 25.sdp,
            title = stringResource(id = R.string.choice_specie),
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
    }

}

@Preview
@Composable
fun PreviewHeader() {
    Header("Jorge")
}