package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.Email
import com.soujunior.petjournal.ui.forgotPasswordScreen.ForgotPasswordScreenViewModel
import com.soujunior.petjournal.ui.forgotPasswordScreen.sendRequestToChangePassword
import com.soujunior.petjournal.ui.states.States.localEmailError
import com.soujunior.petjournal.ui.states.States.localEmailState

@Composable
fun ForgotPasswordScreenMain(
    navController: NavController,
    forgotPasswordScreenViewModel: ForgotPasswordScreenViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                CreateTitleAndImageLogo(
                    title = "Esqueceu a senha?",
                    modifierImage = Modifier.size(200.dp)
                )
            }
            item {
                Text(
                    text = "Redefina a sua senha em duas etapas",
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
                )
            }
            item {
                Spacer(modifier = Modifier.height(70.dp))
                Email(textTop = "Qual o seu email de cadastro ?")
                Spacer(modifier = Modifier.height(70.dp))
            }
            item {
                val email by localEmailState.current
                val emailError by localEmailError.current

                val enableButton = if (email.isNotBlank()) (!emailError) else false

                Button(
                    text = "Enviar",
                    enableButton = enableButton,
                    submit = {
                        sendRequestToChangePassword(
                            ForgotPasswordModel(email = email),
                            forgotPasswordScreenViewModel
                        )
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(height = 50.dp, width = 200.dp)
                )
            }
            item {
                Button(
                    text = "Cancelar",
                    enableButton = true,
                    submit = { navController.navigate("login") },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(height = 50.dp, width = 200.dp),
                    inDarkMode = !isSystemInDarkTheme(),
                    setSystemBarColor = false
                )
            }
        }
    }
}

private fun click(
    email: String,
) {
    Log.i(
        "testar", "$email"
    )

}