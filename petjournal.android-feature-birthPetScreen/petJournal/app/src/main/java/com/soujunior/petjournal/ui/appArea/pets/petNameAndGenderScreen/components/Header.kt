package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R


@Composable
fun Header(modifier : Modifier = Modifier.fillMaxWidth(), species: String = "Gato"){
    Spacer(modifier = Modifier.padding(12.dp))

    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            append(stringResource(id = R.string.wow))
        }
        append("\n")
        append("\n")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            append(stringResource(id = R.string.register_message, species))
        }
    }
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
        )

    Spacer(modifier = Modifier.padding(bottom = 20.dp))
}

@Preview(showBackground = true)
@Composable
private fun PreviewHeader(){
    Header()
}