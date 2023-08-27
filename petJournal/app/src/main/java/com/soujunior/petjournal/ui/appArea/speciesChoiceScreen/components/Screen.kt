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
                            /*TODO: (Simão): evite usar a definição de cores manualmente "Color.White"
                                * tente usar o theme o máximo possível para que caso haja alguma mudança no visual, isso possa ser feito diretamente pela paleta de cores que temos do mesmo modo que fez a baixo na linha 72.
                                * Veja se esta implementação abaixo funciona.
                                * buttonColor = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface
                                * )
                                * Fora isso esta tudo bem, só tente criar os detalhes, como uma lógica
                                * que permita saber o item que esta selecionado no momento, etc.
                                * Isso serve tanto para a seleção de espécie quanto para a página que
                                * esta em foco no momento.*/
                            buttonColor = ButtonDefaults.buttonColors(Color.White),
                            textColor = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
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