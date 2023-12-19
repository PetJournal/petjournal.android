package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter


@Composable
fun Header(modifier : Modifier = Modifier, species: String = "Gato"){
    Spacer(modifier = Modifier.padding(12.dp))
    
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.register_message, species.capitalizeFirstLetter()),
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
        )

    Spacer(modifier = Modifier.padding(bottom = 20.dp))
}

@Preview
@Composable
private fun PreviewHeader(){
    Header()
}