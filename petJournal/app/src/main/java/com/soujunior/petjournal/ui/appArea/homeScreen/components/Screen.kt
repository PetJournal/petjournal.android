package com.soujunior.petjournal.ui.appArea.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.components.NavigationBar
import org.koin.androidx.compose.get

/*todo: 05 (gelson) Já temos um scaffold para as telas da area App, a nossa fica na MainActivity, linha 53. Remova a implementação de lá.
*  */






@ExperimentalPagerApi
@Composable
fun Screen(navController: NavController) {
    Scaffold(
        bottomBar = { NavigationBar(navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                ) {
                    item { Header(navController) }
                    item { Body(navController, get()) }
                }
            }
        }
    )

}