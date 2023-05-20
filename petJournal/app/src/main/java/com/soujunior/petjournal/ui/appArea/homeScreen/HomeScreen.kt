package com.soujunior.petjournal.ui.appArea.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.appArea.detailScreen.DetailScreenViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreen(navController: NavController) {
    val HomeScreenViewModel: DetailScreenViewModel = getViewModel()
    /**Conteúdo para tela principal*/
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row {
            Image(
                painter = painterResource(R.drawable.logo_lilac),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(
                        2.0.dp,
                        MaterialTheme.colors.secondary,
                        CircleShape
                    )
                    .clickable {
                        navController.navigate("register")
                    }
            )

        }
        Row {
            Image(
                painter = painterResource(R.drawable.logo_blue),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(
                        2.0.dp,
                        MaterialTheme.colors.secondary,
                        CircleShape
                    )
                    .clickable {
                        navController.navigate("login")
                    }
            )

        }

    }
}
