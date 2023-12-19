package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.InputText

@Composable
fun BoxInputText(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(start= 16.dp, end = 16.dp, bottom = 8.dp),
            text = stringResource(id = R.string.pet_name),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )

        InputText(
            modifier = Modifier,
            textTop = "Nome: ",
            textValue = "Digite aqui...",
            onEvent = {}
        )
    }
}


@Preview
@Composable
private fun PreviewBoxInput(){
    BoxInputText()
}