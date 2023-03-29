package com.soujunior.petjournal.ui.registerScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.util.AlertText
import com.soujunior.petjournal.ui.util.hasSpecialCharOrNumber
import com.soujunior.petjournal.ui.util.isEmail
import com.soujunior.petjournal.ui.util.isValidLenght


private var localNameState = compositionLocalOf { mutableStateOf("") }
private var localLastNameState = compositionLocalOf { mutableStateOf("") }
private var localEmailState = compositionLocalOf { mutableStateOf("") }
private var localPhoneNumberState = compositionLocalOf { mutableStateOf("") }
private var localPasswordState = compositionLocalOf { mutableStateOf("") }
private var localConfirmPasswordState = compositionLocalOf { mutableStateOf("") }
private var localCheckedState = compositionLocalOf { mutableStateOf(false) }

@Composable
fun RegisterScreen(navController: NavController) {
    Code(navController)
}

@Composable
fun Code(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        val name by localNameState.current
        val lastName by localLastNameState.current
        val email by localEmailState.current
        val phoneNumber by localPhoneNumberState.current
        val password by localPasswordState.current
        val confirmPassword by localConfirmPasswordState.current
        val check by localCheckedState.current

        var enableButton = false

        val paddingForm = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)
        val roundedCornerShape = RoundedCornerShape(30.dp)

        CreateTitleAndSubtitle()
        Form(paddingForm, roundedCornerShape)

        if (name.isNotEmpty() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            phoneNumber.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            check
        ) {
            enableButton = true
        }

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
            enabled = enableButton,
            modifier = paddingForm.fillMaxWidth(),
            shape = RoundedCornerShape(topEnd = 20.dp, bottomStart = 20.dp)
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
fun Name(padding: Modifier, roundedCornerShape: RoundedCornerShape) {
    var name by localNameState.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = name,
        onValueChange = { newName ->
            name = newName
            showErrorLenght = isValidLenght(newName)
            showErrorCharacter = hasSpecialCharOrNumber(newName)
        },
        label = { Text("Name") },
        placeholder = { Text("Nome") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        modifier = padding.fillMaxWidth(),
        shape = roundedCornerShape,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        isError = showErrorLenght || showErrorCharacter
    )
    if (showErrorLenght) {
        AlertText(padding = padding, textMessage = "O Nome precisa ter entre 3 e 30 caracteres..")
    }
    if (showErrorCharacter) {
        AlertText(padding = padding, textMessage = "Caracteres especiais não são permitidos!")
    }
}

@Composable
fun LastName(padding: Modifier, roundedCornerShape: RoundedCornerShape) {
    var lastName by localLastNameState.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = lastName,
        onValueChange = { newLastName ->
            lastName = newLastName
            showErrorLenght = isValidLenght(newLastName)
            showErrorCharacter = hasSpecialCharOrNumber(newLastName)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        label = { Text("Last Name") },
        modifier = padding.fillMaxWidth(),
        shape = roundedCornerShape,
        isError = showErrorLenght || showErrorCharacter
    )
    if (showErrorLenght) {
        AlertText(
            padding = padding,
            textMessage = "O Sobrenome precisa ter entre 3 e 30 caracteres.."
        )
    }
    if (showErrorCharacter) {
        AlertText(padding = padding, textMessage = "Caracteres especiais não são permitidos!")
    }
}

@Composable
fun Email(padding: Modifier, roundedCornerShape: RoundedCornerShape) {
    //TODO: Exibe mensagem de erro mas não impede nada
    var email by localEmailState.current
    var inFocus by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

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
        modifier = padding
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus)
                    it.hasFocus
                else {
                    it.hasFocus
                }
            },
        shape = roundedCornerShape,
        isError = error
    )

    if (!inFocus && !isEmail(email) && email.isNotBlank() && (email.length) > 7) {
        error = true
        AlertText(padding = padding, textMessage = "Forneça um email no formato correto")
    } else {
        error = false
    }
}

//TODO: Original
@Composable
fun PhoneNumber(padding: Modifier, roundedCornerShape: RoundedCornerShape) {
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
        shape = roundedCornerShape,
        label = { Text("Telefone") },
        modifier = padding.fillMaxWidth(),
        isError = true
    )
}


@Composable
fun Password(padding: Modifier, roundedCornerShape: RoundedCornerShape) {
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
        shape = roundedCornerShape,
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
fun Form(padding: Modifier, roundedCornerShape: RoundedCornerShape) {
    Name(padding = padding, roundedCornerShape = roundedCornerShape)
    LastName(padding = padding, roundedCornerShape = roundedCornerShape)
    Email(padding = padding, roundedCornerShape = roundedCornerShape)
    PhoneNumber(padding = padding,roundedCornerShape = roundedCornerShape)
    Password(padding = padding, roundedCornerShape = roundedCornerShape)
    PrivacyPolicyCheckbox(padding = padding)
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
    if (check) {
        Log.i(
            "click ->",
            "$name, $lastName, $email, $phoneNumber, $password, $confirmPassword, $check"
        )
    }
}


