package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.domain.model.PetInformationModel

@Composable
fun PetItemCard(info : PetInformationModel){
    /*Nome, Raça, imagem com a espécie*/
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(
                defaultElevation =  10.dp,
           )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Column() {
                info.name?.let { Text(text = it, fontSize = 15.sp) }
                info.petRace?.let { Text(text = it, fontSize = 12.sp) }
            }
        }
    }
}

@Preview
@Composable
private fun previewPetItemCard(){
    val petItem = PetInformationModel(0, name = "Jake Tesouro", petRace = "Pointer")
    PetItemCard(petItem)
}