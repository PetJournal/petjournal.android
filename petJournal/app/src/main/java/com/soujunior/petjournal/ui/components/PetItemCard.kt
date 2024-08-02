@file:OptIn(ExperimentalFoundationApi::class)

package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.PetInformationResponse
import com.soujunior.domain.model.response.pet_information.PetInformationItem
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.util.calculateAge
import com.soujunior.petjournal.ui.util.formatAge

@Composable
fun PetItemCard(info : PetInformationItem, modifier: Modifier){
    /*Nome, Raça, imagem com a espécie*/

    Card(modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = if(info.gender == "M") BorderStroke(2.dp, Color(0xFF8093F1)) else BorderStroke(2.dp, Color(0xFFFFB8EB)),
        elevation = CardDefaults.cardElevation(
                defaultElevation =  10.dp,
           )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.agenda),
                    contentDescription = "Imagem do pet",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.LightGray, CircleShape)
                )
                Column(Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                    info.petName?.let { Text(text = it, fontSize = 15.sp, color = if(info.gender == "M") Color(0xFF5888ED) else Color(0xFF9A0963)) }
                    info.gender?.let { Text(text = it, fontSize = 12.sp) }
                    info.dateOfBirth?.let { Text(text = formatAge(calculateAge(it)), fontSize = 12.sp) }
                }
                Column (
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.End){
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Delete Pet Data")
                    Spacer(modifier = Modifier.padding(vertical = 2.5.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Go for pet screen")
                }
            }
        }
    }


}



@Preview
@Composable
private fun PreviewPetItemCard(){
//    val petItem = PetInformationModel(0, name = "Jake Tesouro", petRace = "Pointer", petAge = "10", gender = "F")
//    PetItemCard(petItem, Modifier)
}
