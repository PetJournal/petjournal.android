package com.soujunior.petjournal.ui.appArea.homeScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.appArea.homeScreen.components.Screen
import org.koin.androidx.compose.get

@ExperimentalPagerApi
@Composable
fun HomeScreen(navController: NavController) {
    Screen(navController, get())
}

