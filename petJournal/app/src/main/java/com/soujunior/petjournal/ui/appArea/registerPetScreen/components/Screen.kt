package com.soujunior.petjournal.ui.appArea.registerPetScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.util.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Screen(navController: NavController, userViewModel: UserViewModel) {
    Scaffold(

        bottomBar = { NavigationBar(navController) },
        content = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    //.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .padding(horizontal = 10.dp)
                    .background(MaterialTheme.colorScheme.background),
                content = {
                    item { Header() }
                    item { GridVectors() }
                    item {
                        Button2(submit = {
                            navController.navigate("speciesChoice")
                        }, enableButton = true, text = "Continuar")
                    }
                })
        })
}