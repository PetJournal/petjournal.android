package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Header()
                    GridVectors()
                    Spacer(modifier = Modifier.height(5.dp))
                    Button2(
                        submit = { /*TODO*/ },
                        modifier = Modifier.width(180.dp),
                        enableButton = false,
                        text = "Outros...",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button2(
                            submit = { /*TODO*/ },
                            modifier = Modifier.width(150.dp),
                            enableButton = true,
                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
                            text = "Voltar",
                            buttonColor = ButtonDefaults.buttonColors(Color.White),
                            textColor = MaterialTheme.colorScheme.primary
                        )
                        Button2(
                            submit = { /*TODO*/ },
                            modifier = Modifier.width(150.dp),
                            enableButton = true,
                            text = "Continuar"
                        )

                    }
                }
            }
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