package com.soujunior.petjournal.ui.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
//import com.soujunior.petjournal.ui.theme.primaryColor
//import com.soujunior.petjournal.ui.theme.whiteBackgroundColor
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val LoginScreenViewModel: LoginScreenViewModel = getViewModel()

    val emailField = remember { mutableStateOf("") }
    val passwordField = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }
    var checked by remember {
        mutableStateOf(true)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color.White),
            ,contentAlignment = Alignment.TopCenter
        ) {
            /*Icon(painter = painterResource(id = R.drawable.logo_pet_journal), contentDescription = null )*/
            Image(
                painter = painterResource(id = R.drawable.logo_pet_journal_white),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(40.dp))
        }
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                OutlinedTextField(
                    value = emailField.value,
                    onValueChange = { emailField.value = it },
                    label = { Text(text = "Digitar e-mail") },
                    placeholder = { Text(text = "Digitar e-mail") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.padding(8.dp))

                val visualTransformation = if (isPasswordVisible.value)
                    VisualTransformation.None
                else PasswordVisualTransformation()

                OutlinedTextField(
                    value = passwordField.value,
                    onValueChange = { passwordField.value = it },
                    label = { Text(text = "Digitar senha") },
                    visualTransformation = visualTransformation,
                    trailingIcon = {
                        if (passwordField.value.isNotBlank()) {
                            PasswordVisibleIcon(isPasswordVisible)
                        } else null
                    },
                    placeholder = { Text(text = "Digitar senha") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    verticalAlignment = Alignment.CenterVertically,
                    //horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = checked, onCheckedChange = { checked_ ->
                                checked = checked_
                            })
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

                /*Spacer(modifier = Modifier.padding(80.dp))*/
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {},
                    /*colors = ButtonDefaults.buttonColors(
                        //backgroundColor = primaryColor,
                        contentColor = Color.White
                    )*/
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
                        .fillMaxWidth(0.8f)
                        .height(60.dp)
                ) {
                    Text(
                        text = "Acessar conta", letterSpacing = 0.75.sp, fontSize = 20.sp
                    )

                }
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Não tem uma conta? Inscrever-se",
                    modifier = Modifier.clickable(onClick = {})
                )
                Spacer(modifier = Modifier.padding(10.dp))


            }

        }
    }

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
