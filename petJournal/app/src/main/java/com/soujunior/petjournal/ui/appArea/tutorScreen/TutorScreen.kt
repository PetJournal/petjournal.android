package com.soujunior.petjournal.ui.appArea.tutorScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.NavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun TutorScreen(navController: NavController){
    Scaffold(

        bottomBar = { NavigationBar(navController)},
        content = {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Tela do Tutor Temporaria", fontSize = 50.sp)}
        }

    )
}