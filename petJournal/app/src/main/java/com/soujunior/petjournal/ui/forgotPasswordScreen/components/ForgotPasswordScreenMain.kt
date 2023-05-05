package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.petjournal.ui.forgotPasswordScreen.ForgotPasswordScreenViewModel
import com.soujunior.petjournal.ui.forgotPasswordScreen.postForm
import com.soujunior.petjournal.ui.states.States.localEmailError
import com.soujunior.petjournal.ui.states.States.localEmailState

@Composable
fun ForgotPasswordScreenMain(navController: NavController, forgotPasswordScreenViewModel : ForgotPasswordScreenViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                val padding = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp)
                val roundedCornerShape = RoundedCornerShape(5.dp)
                CreateLogoView()
                TextViewCompH2(text = "Esqueceu a senha?", pStart = 8, pTop = 16)
                TextViewCompBody1(
                    text = "Redefina a sua senha em duas etapas",
                    pStart = 8,
                    pTop = 8,
                    alignment = Alignment.CenterVertically,
                    arrangement = Arrangement.Center,
                    withTheme = false
                )
                Form(padding, roundedCornerShape)
            }
            item {

                val email by localEmailState.current
                val emailError by localEmailError.current
                var enableButton = false
                val padding = Modifier.padding(start = 20.dp, end = 20.dp, top = 70.dp)

                enableButton =
                    if (email.isNotBlank()
                    ) {
                        (!emailError)
                    } else {
                        false
                    }
                ComponentButton(
                    enableButton = enableButton,
                    modifier = padding,
                    text = "Enviar",
                    top = 70,
                    submit = {
                        postForm(
                        ForgotPasswordModel(

                            email = email
                        ),
                            forgotPasswordScreenViewModel
                    )
                    },
                    withTheme = true
                )
                ComponentButton(
                    enableButton = true,
                    submit = {navController.navigate("login")},
                    modifier = padding,
                    text = "Cancelar",
                    top = 8,
                    withTheme = false
                )
            }
        }
    }
}

private fun click(
    email: String,
) {
    Log.i(
        "testar", "$email")

}