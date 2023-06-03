package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordScreenViewModel
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.Email


//TODO: (Gelson) trocar o ForgotPassMain por Screen pra dar uma padronizada
@Composable
fun ForgotPassMain (navController: NavController, viewModel: ForgotPasswordScreenViewModel) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .align(Alignment.TopCenter)
        ) {
            item {
                CreateTitleAndImageLogo(
                    title = "Esqueceu a senha?",
                    styleTitle = MaterialTheme.typography.displayMedium,
                    modifierImage = Modifier
                        .size(width = 200.dp, height = 200.dp)
                        .padding(top = 20.dp),
                )

            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Redefina a sua senha em duas etapas",
                        style = MaterialTheme.typography.titleLarge

                    )
                }
            }
            item { Spacer(modifier = Modifier.padding(top = 70.dp)) }
            // TODO: (Gelson) Durante a refatoração pra seguir o modelo atual, o campo Email a baixo precisará ser refatorado pra receber os elementos state
            // TODO: em vista disso, foi criado um modelo de Input que pode ser usado em várias situações e acredito que para o email esse deve bastar
            /**
            InputText(
            textTop = "Email",
            textHint = "eg: exemple@petjournal.com",
            textValue = viewModel.state.email,
            textError = viewModel.state.emailError,
            onEvent = { it : String -> viewModel.onEvent(RegisterFormEvent.EmailChanged(it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email) )
             */
            item { Email(textTop = "Qual seu e-mail de cadastro?") }
            item { Spacer(modifier = Modifier.padding(top = 70.dp)) }
            //TODO: (Gelson) no Footer tem dois botões, um cancelar e um pra continuar.
            item { Footer(navController, viewModel)}
        }
    }
}