package com.soujunior.petjournal.ui.registerScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private var localNameState = compositionLocalOf { mutableStateOf("") }
private var localLastNameState = compositionLocalOf { mutableStateOf("") }
private var localEmailState = compositionLocalOf { mutableStateOf("") }
private var localPhoneNumberState = compositionLocalOf { mutableStateOf("") }
private var localPasswordState = compositionLocalOf { mutableStateOf("") }
private var localConfirmPasswordState = compositionLocalOf { mutableStateOf("") }
private var localCheckedState = compositionLocalOf { mutableStateOf(false) }

@Composable
fun RegisterScreen(navController: NavController) {
    code(navController)
}

@Composable
fun code(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        var name by localNameState.current
        var lastName by localLastNameState.current
        var email by localEmailState.current
        var phoneNumber by localPhoneNumberState.current
        var password by localPasswordState.current
        var confirmPassword by localConfirmPasswordState.current
        var check by localCheckedState.current

        val paddingForm = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)

        CreateTitleAndSubtitle()
        Form(paddingForm)

        Button(
            onClick = {
                click(
                    name,
                    lastName,
                    email,
                    phoneNumber,
                    password,
                    confirmPassword,
                    check
                )
            },
            modifier = paddingForm.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp)
            ) {
            Text(text = "Cadastrar", fontSize = 20.sp)
        }
    }
}

@Composable
fun CreateTitleAndSubtitle() {
    val padding = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)
    Text(
        text = "PetJournal",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )

    Text(
        text = "Inscreva-se",
        fontSize = 25.sp,
        modifier = padding
    )
}

@Composable
fun Name(padding: Modifier) {
    var name by localNameState.current
    OutlinedTextField(
        value = name,
        onValueChange = { newName -> name = newName },
        label = { Text("Name") },
        placeholder = { Text("Nome") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        modifier = padding.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp)
        )
}

@Composable
fun LastName(padding: Modifier) {
    var lastName by localLastNameState.current
    OutlinedTextField(
        value = lastName,
        onValueChange = { newLastName -> lastName = newLastName },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        label = { Text("Last Name") },
        modifier = padding.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
    )
}

@Composable
fun Email(padding: Modifier) {
    var email by localEmailState.current
    OutlinedTextField(
        value = email,
        onValueChange = { newEmail -> email = newEmail },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        label = { Text("Email") },
        modifier = padding.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
    )
}

@Composable
fun PhoneNumber(padding: Modifier) {
    var phoneNumber by localPhoneNumberState.current
    OutlinedTextField(
        value = phoneNumber,
        onValueChange = { newPhoneNumber -> phoneNumber = newPhoneNumber },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        shape = RoundedCornerShape(30.dp),
        label = { Text("Telefone") },
        modifier = padding.fillMaxWidth()
    )
}

@Composable
fun Password(padding: Modifier) {
    var password by localPasswordState.current
    var confirmPassword by localConfirmPasswordState.current

    //TODO: Adicionar verificação de caracteres
    OutlinedTextField(
        value = password,
        onValueChange = { newPassword -> password = newPassword },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        label = { Text("Senha") },
        shape = RoundedCornerShape(30.dp),
        modifier = padding.fillMaxWidth()
    )

    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { newConfirmPassword -> confirmPassword = newConfirmPassword },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        label = {
            Text("Confirmar Senha")
        },
        shape = RoundedCornerShape(30.dp),
        modifier = padding.fillMaxWidth()
    )
}

@Composable
fun PrivacyPolicyCheckbox(padding: Modifier) {
    var checked by localCheckedState.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { it -> checked = it },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                uncheckedColor = Color.Gray
            ),
            modifier = Modifier.padding(start = 8.dp, end = 20.dp, top = 16.dp)
            )
        Text(
            text = "Eu concordo com a política de privacidade",
            modifier = padding.fillMaxWidth()
        )
    }
}

@Composable
fun Form(padding: Modifier) {
    Name(padding)
    LastName(padding)
    Email(padding)
    PhoneNumber(padding)
    Password(padding)
    PrivacyPolicyCheckbox(padding)
}

fun click(
    name: String,
    lastName: String,
    email: String,
    phoneNumber: String,
    password: String,
    confirmPassword: String,
    check: Boolean,
) {
    Log.i("click ->", "$name, $lastName, $email, $phoneNumber, $password, $confirmPassword, $check")
}


