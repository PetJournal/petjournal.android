package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.components

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
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter


@Composable
fun Header(name: String = "fulano", modifier: Modifier = Modifier) {

    Spacer(modifier = Modifier.padding(12.dp))

    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(stringResource(id = R.string.hello_user))
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.surfaceTint)) {
            append(name.capitalizeFirstLetter())
        }

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(stringResource(id = R.string.choice_specie))
        }
    }

    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )

    Spacer(modifier = Modifier.padding(bottom = 20.dp))

}

@Preview
@Composable
fun PreviewHeader() {
    Header()
}