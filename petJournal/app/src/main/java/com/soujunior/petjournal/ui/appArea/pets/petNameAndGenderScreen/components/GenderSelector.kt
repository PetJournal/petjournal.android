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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.AlertText
import com.soujunior.petjournal.ui.components.RoundedSquare


@Composable
fun GenderSelector(
    selectedGender: (String) -> Unit,
    clearSelection: () -> Boolean,
    textError: List<String>? = null,
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.pet_gender),
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp))
            }
            
            GenderButtons(selectedGender, clearSelection, textError)
        })
}

@Composable
private fun GenderButtons(
    selectedGender: (String) -> Unit,
    clearSelection: () -> Boolean,
    textError: List<String>? = null,
){

    var selectedItem by remember { mutableStateOf("") }
    if (clearSelection()) {
        selectedItem = ""
        selectedGender("")
    }

    Row(
        modifier = Modifier.padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        RoundedSquare(
            text = stringResource(id = R.string.pet_gender_male),
            isSelected = selectedItem == "M",
            size = 150.dp,
            topLeftRadius = 32.dp,
            topRightRadius = 32.dp,
            bottomLeftRadius = 32.dp,
            bottomRightRadius = 32.dp,
            image = painterResource(id = R.drawable.icone_macho),
            selectedColor = if(selectedItem == "M") MaterialTheme.colorScheme.primary else Color.Transparent,
            colorBackground =  Color.Transparent,
            onClick = {
                selectedItem = "M"
                selectedGender("M")
            }
        )

        RoundedSquare(
            text = stringResource(id = R.string.pet_gender_female),
            isSelected = selectedItem == "F",
            size = 150.dp,
            topLeftRadius = 32.dp,
            topRightRadius = 32.dp,
            bottomLeftRadius = 32.dp,
            bottomRightRadius = 32.dp,
            image = painterResource(id = R.drawable.icone_femea),
            selectedColor = if(selectedItem == "F") MaterialTheme.colorScheme.primary else Color.Transparent,
            colorBackground =  Color.Transparent,
            onClick = {
                selectedItem = "F"
                selectedGender("F")
            }
        )
    }
    Row(modifier = Modifier.fillMaxWidth()){
        if(textError != null) {
            textError.forEach {
                AlertText(textMessage = it, modifier = Modifier.padding(10.dp))
            }
        }
        else{
            Text(
                text = stringResource(id = R.string.required_field),
                modifier = Modifier.padding(start = 2.dp, top = 10.dp),
                fontSize = 15.sp,
                textAlign = TextAlign.Start)
        }
    }
}


@Preview
@Composable
fun m(){
    GenderSelector(
        selectedGender = {},
        clearSelection = {true},
        textError = null
    )
}