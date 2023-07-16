package com.soujunior.petjournal.ui.appArea.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CardButton


@Composable
fun Menu(navController: NavController) {
    val darkTheme = isSystemInDarkTheme()
    val imageSize = 140.dp
    Column(

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CardButton(
                image = painterResource(id = R.drawable.agenda),
                cardColor = Color(0xFF8093F1),
                submit = { navController.navigate("login") },
                modifier = Modifier.size(imageSize)
            )
            CardButton(
                image = painterResource(id = R.drawable.servicos),
                cardColor = if (darkTheme) Color(0xFFFF4081) else Color(0xFF9A0963),
                submit = { navController.navigate("login") },
                modifier = Modifier.size(imageSize)
            )
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CardButton(
                image = painterResource(id = R.drawable.vacinas),
                cardColor = Color(0xFFAFD9DB),
                submit = { navController.navigate("login") },
                modifier = Modifier.size(imageSize)
            )
            CardButton(
                image = painterResource(id = R.drawable.vermifugos),
                cardColor = Color(0xFFFFB8EB),
                submit = { navController.navigate("login") },
                modifier = Modifier.size(imageSize)
            )
        }

    }
}