package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.states.States.localEmailError
import com.soujunior.petjournal.ui.states.States.localEmailState

@Composable
fun MyApp(navController: NavController) {

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
                CreateLogoView(
                    logo = R.drawable.logo_purple,
                    width = 200, height = 200,
                    top = 50, alignment = Alignment.CenterVertically,
                    arrangement = Arrangement.Center,

                    )
                TextViewCompH2(text = "Esqueceu a senha?", pStart = 8, pTop = 16)
                TextViewCompBody1(
                    text = "Redefina a sua senha em duas etapas",
                    pStart = 8,
                    pTop = 8,
                    color = Color.Black,
                    alignment = Alignment.CenterVertically,
                    arrangement = Arrangement.Center
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
                    top = 70, border = null,
                    colorButton = ButtonDefaults.buttonColors(backgroundColor = Color(0xFB9A0963)),
                    colorText = Color.White,
                    submit = { click(email) },
                )
                ComponentButton(
                    enableButton = true,
                    submit = { click("Cancelado") },
                    modifier = padding,
                    text = "Cancelar",
                    top = 8,
                    border = BorderStroke(2.dp, Color(0xFB9A0963)),
                    colorButton = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    colorText = Color(0xFB9A0963)
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