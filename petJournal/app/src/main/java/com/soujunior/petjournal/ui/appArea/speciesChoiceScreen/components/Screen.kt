package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.util.UserViewModel

@Composable
fun Screen(navController: NavController, userViewModel: UserViewModel) {

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
// Não é exibido
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.pet_registration),
                fontSize = 24.sp,
                color = Color.Black
            )
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Header()
                GridVectors() {
                    Log.i("MyTag", it)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button2(
                    submit = { /*TODO*/ },
                    modifier = Modifier.width(180.dp),
                    enableButton = true,
                    text = stringResource(R.string.others),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button2(
                        submit = { /*TODO*/ },
                        modifier = Modifier.width(150.dp),
                        enableButton = true,
                        border = BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        text = stringResource(R.string.back),
                        buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
                        textColor = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button2(
                        submit = { /*TODO*/ },
                        modifier = Modifier.width(150.dp),
                        enableButton = true,
                        text = stringResource(R.string.text_continue)
                    )

                }
            }
        }
        // Verificar fica na posição correta
        NavigationBar(navController)
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    val userViewModel: UserViewModel = viewModel()
    val navController = rememberNavController()
    Screen(navController = navController, userViewModel = userViewModel)
}