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
    //TODO: (Gelson) 8: .
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
                //TODO: (Gelson) 9: o usuário não deve consegui voltar para a rota de AccountManager a partir do ponto que chega na tela home.
                submit = { navController.navigate("login") },
                modifier = Modifier.size(imageSize)
            )
            CardButton(
                image = painterResource(id = R.drawable.servicos),
                //TODO: (Gelson) 10: .
                cardColor = if (darkTheme) Color(0xFFFF4081) else Color(0xFF9A0963),
                //TODO: (Gelson) 11: mesma situação do 9.
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
                //TODO: (Gelson) 11: mesma situação do 9 e 10
                submit = { navController.navigate("login") },
                modifier = Modifier.size(imageSize)
            )
            CardButton(
                image = painterResource(id = R.drawable.vermifugos),
                cardColor = Color(0xFFFFB8EB),
                //TODO: (Gelson) 12: 9, 10, 11.
                submit = { navController.navigate("login") },
                modifier = Modifier.size(imageSize)
            )
        }

    }
}