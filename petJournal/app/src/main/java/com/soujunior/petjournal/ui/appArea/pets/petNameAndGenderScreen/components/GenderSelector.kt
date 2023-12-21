package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.RoundedSquare


@Composable
fun GenderSelector() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            Row {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Qual o Sexo do seu animal?",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.fillMaxWidth().padding(25.dp))
            }
            
            GenderButtons()
        })
}

@Composable
private fun GenderButtons(){
    Row(
        modifier = Modifier.padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        RoundedSquare(
            modifier = Modifier,
            text = stringResource(id = R.string.pet_gender_male),
            isSelected = false,
            size = 150.dp,
            topLeftRadius = 32.dp,
            topRightRadius = 32.dp,
            bottomLeftRadius = 32.dp,
            bottomRightRadius = 32.dp,
            image = painterResource(id = R.drawable.icone_macho),
            material = MaterialTheme.colorScheme.background
        )

        RoundedSquare(
            modifier = Modifier,
            text = stringResource(id = R.string.pet_gender_female),
            isSelected = true,
            size = 150.dp,
            topLeftRadius = 32.dp,
            topRightRadius = 32.dp,
            bottomLeftRadius = 32.dp,
            bottomRightRadius = 32.dp,
            image = painterResource(id = R.drawable.icone_femea),
            material = MaterialTheme.colorScheme.background
        )
    }
    Row(modifier = Modifier.fillMaxWidth()){
        Text(
            text ="*Campo Obrigatório.",
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 2.dp, top = 10.dp),
            fontSize = 15.sp,
            textAlign = TextAlign.Start)
    }
}

@Preview
@Composable
private fun PreviewGS(){
    GenderSelector()
}