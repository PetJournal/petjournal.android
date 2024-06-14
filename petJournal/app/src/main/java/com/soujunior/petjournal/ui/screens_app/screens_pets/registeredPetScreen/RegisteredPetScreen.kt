package com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen.ViewModelRegisteredPets
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen.components.Screen
import com.soujunior.petjournal.ui.states.TaskState
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisteredPetScreen(navController: NavController){
    Screen(navController)
}