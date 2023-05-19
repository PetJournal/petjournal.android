package com.soujunior.petjournal.ui.loginScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.loginScreen.LoginScreenViewModel
import com.soujunior.petjournal.ui.loginScreen.postFormLogin
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.util.isEmail
import org.koin.androidx.compose.getViewModel

@Composable
fun Footer(navController: NavController) {
    val email by States.localEmailState.current
    val password by States.localPasswordState.current
    val checkBox by States.checked.current
    val loginScreenViewModel: LoginScreenViewModel = getViewModel()
    var isLoginVisible = false
    if (isEmail(email) && password.length >= 8) isLoginVisible = true

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Button(
                text = "Continuar",
                submit = {
                    postFormLogin(
                        form = LoginModel(email = email, password = password),
                        loginScreenViewModel = loginScreenViewModel,
                        checkBox
                    )
                },
                enableButton = isLoginVisible,
                modifier = Modifier.size(height = 50.dp, width = 240.dp)
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Não tem uma conta? Inscreva-se",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clickable(onClick = { navController.navigate("register") })
                    .padding(top = 20.dp),
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
            )
        }
    }
}