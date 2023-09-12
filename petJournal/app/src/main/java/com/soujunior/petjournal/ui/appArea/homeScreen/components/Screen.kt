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
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.components.NavigationBar

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

            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                ) {
                    item { Header(navController as NavHostController) }
                    item { Body(navController) }
                }
            }
        }
    )

}