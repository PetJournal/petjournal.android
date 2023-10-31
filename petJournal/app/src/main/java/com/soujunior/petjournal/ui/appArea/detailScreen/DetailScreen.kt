package com.soujunior.petjournal.ui.appArea.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailScreen(navController: NavController) {
    val DetailScreenViewModel: DetailScreenViewModel = getViewModel()
    Box(
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
        ,
        contentAlignment = Alignment.Center
    ) {
            Row(modifier = Modifier.width(IntrinsicSize.Max)) {
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
                            navController.navigate("home")
                        }
                )
            }
    }
    /**Conteúdo para tela secundária*/
}
