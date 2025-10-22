package com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel

@Composable
fun Screen(navController: NavController){
    val viewModel : TaskListViewModel = getViewModel()
}