package com.soujunior.petjournal.ui.loginScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.petjournal.ui.registerScreen.RegisterScreenViewModel
import com.soujunior.petjournal.ui.registerScreen.components.Email
import com.soujunior.petjournal.ui.registerScreen.components.ImageLogo
import com.soujunior.petjournal.ui.registerScreen.components.Password
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.light_primary
import com.soujunior.petjournal.ui.util.isEmail
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val loginScreenViewModel: LoginScreenViewModel = getViewModel()
    handleLoginResponse(loginScreenViewModel)
    MyApp(navController)
}

@Composable
fun MyApp(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Header()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.80f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .padding(10.dp)
                .background(MaterialTheme.colors.background)
        ) {
            Text(
                text = "Acessar conta",
                style = MaterialTheme.typography.h2,
                )
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Form()
            Spacer(modifier = Modifier.padding(top = 100.dp))
            Footer(navController)
        }
    }
}

@Composable
private fun Footer(navController: NavController) {
    val email by States.localEmailState.current
    val password by States.localPasswordState.current
    val loginScreenViewModel: LoginScreenViewModel = getViewModel()
    var isLoginVisible = false

    if (isEmail(email) && password.length >= 8) {
        isLoginVisible = true
    }

    ButtonLogin(isLoginVisible = isLoginVisible, loginScreenViewModel)
    Spacer(modifier = Modifier.padding(top = 20.dp))

    Text(
        text = "Não tem uma conta? Inscrever-se",
        style = MaterialTheme.typography.body1,
        modifier = Modifier.clickable(onClick = { navController.navigate("register") })
    )
}

@Composable
private fun Header() {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        ImageLogo()
    }
}

@Composable
private fun Form() {
    Email(isRegister = false)
    Password(isRegister = false)
    RememberForgotPassword()
}

//TODO: Adicionar esse state em um local separado
@Composable
fun RememberForgotPassword() {
    var checked by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier.fillMaxWidth(0.95f),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked_ -> checked = checked_ },
                    colors = CheckboxDefaults.colors(Color(light_primary.value))
                )
                Text(
                    text = "Lembrar",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
        Text(
            text = "Esqueci minha senha",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .clickable(onClick = {}),
        )
    }
}

@Composable
fun handleLoginResponse(loginScreenViewModel: LoginScreenViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    SideEffect {
        loginScreenViewModel.loginSuccess.observe(lifecycleOwner) { success ->
            //TODO: Sucesso ->
            // Dialog com parabens
            // Chamar metodo de login logo em seguida
            // Ser encaminhado para a tela Home
            Toast.makeText(context, "Mensagem de sucesso: $success", Toast.LENGTH_SHORT).show()
        }
        loginScreenViewModel.loginError.observe(lifecycleOwner) { error ->
            Toast.makeText(context, "Mensagem de Erro: $error", Toast.LENGTH_SHORT).show()
            //TODO: Falha ->
            // Exibir uma mensagem avisando do erro
        }
    }
}

@Composable
private fun ButtonLogin(isLoginVisible: Boolean, loginScreenViewModel: LoginScreenViewModel) {
    var email by States.localEmailState.current
    var password by States.localPasswordState.current
    Button(
        onClick = {
            postFormLogin(form = LoginModel(email = email, password = password), loginScreenViewModel = loginScreenViewModel)
                  },
        enabled = isLoginVisible,
        colors = ButtonDefaults.buttonColors(

        ),
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
            .fillMaxWidth(0.6f)
            .height(60.dp)
    ) {
        Text(
            text = "Continuar",
            style = MaterialTheme.typography.button,
        )
    }
}

private fun postFormLogin(form: LoginModel, loginScreenViewModel: LoginScreenViewModel) {
    loginScreenViewModel.postForm(form)
}