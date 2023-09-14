package com.soujunior.petjournal.ui.appArea.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.appArea.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.components.NavigationBar

@ExperimentalPagerApi
@Composable
fun Screen(navController: NavController, viewModel: HomeScreenViewModel) {
    Scaffold(
        bottomBar = { NavigationBar(navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),

            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                ) {
                    item { Header(navController) }
                    item { val carouselImages = viewModel.carouselImages

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
                        } }
                }
            }
        }
    )

}