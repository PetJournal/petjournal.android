package com.soujunior.petjournal.ui.appArea.registerPetScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.util.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Screen(navController: NavController, userViewModel : UserViewModel) {
    Scaffold(

        bottomBar = { NavigationBar(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    //.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .padding(horizontal = 32.dp)
                    .padding(top = 32.dp)
                    .background(MaterialTheme.colorScheme.background),
                content = {
                    Header()
                    GridVectors()
                    Button2(submit = { /*TODO*/ }, enableButton = true, text = "Continuar")
                })
        })
}