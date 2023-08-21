package com.soujunior.petjournal.ui.appArea.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.appArea.homeScreen.HomeScreenViewModel

//todo: 03 (gelson) não esquecer de conseguir uma solução para as anotações experimentais.
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Body(navController: NavController, viewModel: HomeScreenViewModel) {
    val carouselImages = viewModel.carouselImages

    Column() {

        Carousel(imageIds = carouselImages)

        Spacer(modifier = Modifier.padding(top = 16.dp))

        Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

            Text(
                text = "Serviços",
                modifier = Modifier.weight(0.8f),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Ver mais",
                modifier = Modifier.clickable { },
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            Menu(navController)
        }
    }
}