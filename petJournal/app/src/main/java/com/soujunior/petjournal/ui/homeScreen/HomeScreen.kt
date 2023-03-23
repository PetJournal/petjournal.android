package com.soujunior.petjournal.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.detailScreen.DetailScreenViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreen(navController: NavController) {
    val HomeScreenViewModel: DetailScreenViewModel = getViewModel()
    /**Conteúdo para tela principal*/
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.avatar_limpo),
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
