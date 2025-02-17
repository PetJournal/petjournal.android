package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R


@Composable
fun ImagePet(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .clickable {
                // TODO: Colocar funcao de abrir galeria e tirar foto
            },
        contentAlignment = Alignment.Center
    ) {
        // TODO: Colocar icone de imagem vazia corretamente
        Icon(
            painter = painterResource(id = R.drawable.logo_black),
            contentDescription = "Pet Icon",
            tint = Color.Gray,
            modifier = Modifier.size(48.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            // TODO: Colocar icone de editar corretamente
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "Edit Icon",
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}


@Preview(showBackground = false, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun ImagePetPreview() {
    ImagePet()
}


