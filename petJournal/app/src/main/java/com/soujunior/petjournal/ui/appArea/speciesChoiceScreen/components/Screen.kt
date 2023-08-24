package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
                        Button2(
                            submit = { /*TODO*/ },
                            modifier = Modifier.width(180.dp),
                            enableButton = false,
                            text = "Outros..."
                        )
                    }
                    item {
                        Button2(
                            submit = { /*TODO*/ },
                            modifier = Modifier.width(150.dp),
                            enableButton = true,
                            text = "Voltar"
                        )
                    }
                    item {
                        Button2(
                            submit = { /*TODO*/ },
                            modifier = Modifier.width(150.dp),
                            enableButton = true,
                            text = "Continuar"
                        )
                    }
                })
        }
    )
}

@Preview
@Composable
fun Preview() {
    val userViewModel: UserViewModel = viewModel()
    val navController = rememberNavController()
    Screen(navController = navController, userViewModel = userViewModel)
}