package com.soujunior.petjournal.ui.loginScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo

@Composable
fun MyApp(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .align(Alignment.TopCenter)
        ) {
            item {
                CreateTitleAndImageLogo(
                    title = "Acessar conta",
                    styleTitle = MaterialTheme.typography.displayLarge,
                    modifierImage = Modifier
                        .size(width = 200.dp, height = 200.dp)
                        .padding(top = 20.dp),
                )
            }
            item { Spacer(modifier = Modifier.padding(top = 50.dp)) }
            item { Form(navController) }
            item { Spacer(modifier = Modifier.padding(top = 70.dp)) }
            item { Footer(navController) }
        }
    }
}