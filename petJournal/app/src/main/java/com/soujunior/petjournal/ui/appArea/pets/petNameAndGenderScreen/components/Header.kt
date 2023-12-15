package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Header(modifier : Modifier = Modifier, species: String = "Gato"){
    Spacer(modifier = Modifier.padding(12.dp))
    
    Text(text = )
}

@Preview
@Composable
private fun PreviewHeader(){
    Header()
}