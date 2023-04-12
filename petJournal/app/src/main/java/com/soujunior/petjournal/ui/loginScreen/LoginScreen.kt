package com.soujunior.petjournal.ui.loginScreen

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.primaryColor
import com.soujunior.petjournal.ui.theme.whiteBackgroundColor
import com.soujunior.petjournal.ui.util.isValidEmail

import org.koin.androidx.compose.getViewModel

private var localEmailState = compositionLocalOf { mutableStateOf("") }
private var localPasswordState = compositionLocalOf { mutableStateOf("") }
//private var localPasswordVisibleState = compositionLocalOf { mutableStateOf(false) }

@Composable
fun LoginScreen(navController: NavController) {
    MyApp(navController)
}

@Composable
fun MyApp(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Header()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            //Pra escalar na tela de registro verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.80f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                //.background(whiteBackgroundColor)
                .padding(10.dp)
        ) {
            Text(
                text = "Acessar conta",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.padding(20.dp))

            //Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Form()
            Spacer(modifier = Modifier.padding(80.dp))
            //}
            Footer()
        }

    }

}

@Composable
private fun Footer() {
    var email by localEmailState.current
    var password by localPasswordState.current
    var isLoginVisible = false

    /*if (email.isNotEmpty() && password.isNotEmpty()) {
        isLoginVisible = true
    }*/
    if (isValidEmail(email) && password.length>=8) {
        isLoginVisible = true
    }

    ButtonLogin(isLoginVisible = isLoginVisible)

    Spacer(modifier = Modifier.padding(20.dp))

    Text(
        text = "Não tem uma conta? Inscrever-se",
        modifier = Modifier.clickable(onClick = {})
    )
}

@Composable
private fun ButtonLogin(isLoginVisible: Boolean) {
    Button(
        onClick = {},
        enabled = isLoginVisible,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = primaryColor,
            contentColor = Color.White
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
            .fillMaxWidth(0.8f)
            .height(60.dp)
    ) {
        Text(
            text = "Acessar conta", letterSpacing = 0.75.sp, fontSize = 20.sp
        )
    }
}

@Composable
private fun Header() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), contentAlignment = Alignment.TopCenter
    ) {
        /*Icon(painter = painterResource(id = R.drawable.logo_pet_journal), contentDescription = null )*/
        Image(
            painter = painterResource(id = R.drawable.logo_pet_journal_white),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.padding(40.dp))
    }

    Spacer(modifier = Modifier.padding(20.dp))
}

@Composable
private fun Form() {

    Email()
    Password()
    RememberForgotPassword()
}

@Composable
fun RememberForgotPassword() {

    var checked by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = Modifier.fillMaxWidth(0.85f),
        verticalAlignment = Alignment.CenterVertically,
        //horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked_ -> checked = checked_ },
                    colors = CheckboxDefaults.colors(Color(primaryColor.value))

                )
                Text(text = "Lembrar")
            }
        }
        Text(
            text = "Esqueci minha senha",
            modifier = Modifier
                //.align(Alignment.End)
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .clickable(onClick = {}),
        )
    }
}

@Composable
fun Password() {
    var passwordField by localPasswordState.current
    var inFocus by remember { mutableStateOf(false) }
    val isPasswordVisible = remember { mutableStateOf(false) }
    val visualTransformation = if (isPasswordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordField,
        onValueChange = { passwordField = it },
        label = { Text(text = "Digitar senha") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color(0xFFd9d9d9),
            //contentColor = Color.White
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordField.isNotBlank()) {
                PasswordVisibleIcon(isPasswordVisible)
            } else null
        },
        placeholder = { Text(text = "Digitar senha") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .onFocusChanged {
                inFocus = if (it.hasFocus) {
                    it.hasFocus
                } else {
                    it.hasFocus
                }
            }
    )

    if ((!inFocus && passwordField.length <= 8) && passwordField.isNotEmpty()) {
        Text(
            text = "Digite a senha correta",
            modifier = Modifier.padding(32.dp, 8.dp, 16.dp)
        )
    }

}

@Composable
fun Email() {
    var emailField by localEmailState.current
    var inFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = emailField,
        onValueChange = { emailField = it },
        label = { Text(text = "Digitar e-mail") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color(0xFFd9d9d9),
            //contentColor = Color.White
        ),
        placeholder = { Text(text = "Digitar e-mail") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .onFocusChanged {
                inFocus = if (it.hasFocus) {
                    it.hasFocus
                } else {
                    it.hasFocus
                }
            }
    )
    if ((!inFocus && !isValidEmail(emailField)) && emailField.length >= 1) {
        Text(
            text = "Digite o email correto",
            modifier = Modifier.padding(32.dp, 8.dp, 16.dp)
        )
    }

    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
fun PasswordVisibleIcon(passwordVisible: MutableState<Boolean>) {
    val image = if (passwordVisible.value)
        Icons.Default.VisibilityOff
    else
        Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(imageVector = image, contentDescription = "")

    }

}


// Senha tamanho minimo 8

// ABAIXO APOS CLICAR NO BOTAO IRA CONFIRMAR NO SERVIDOR OS DADOS E CASO FOR INCORRETO MOSTRAR E LIMPAR
/*AlertDialog(
            onDismissRequest = { *//* Não faz nada *//* },
            title = { Text("Dados incorretos!") },
            text = { Text("Por favor, verifique o endereço de email, senha e tente novamente.") },
            confirmButton = {
                Button(onClick = { *//* FUNÇÃO LIMPAR CAMPOS EMAIL/SENHA *//* }) {
                    Text("OK")
                }
            }
        )*/
