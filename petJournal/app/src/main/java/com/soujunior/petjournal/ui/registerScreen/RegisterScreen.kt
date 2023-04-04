package com.soujunior.petjournal.ui.registerScreen

import android.util.Log
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.util.*
import com.soujunior.petjournal.ui.util.mask.mobileNumberFilter

private var localNameState = compositionLocalOf { mutableStateOf("") }
private var localNameError = compositionLocalOf { mutableStateOf(false) }
private var localLastNameState = compositionLocalOf { mutableStateOf("") }
private var localLastNameError = compositionLocalOf { mutableStateOf(false) }
private var localEmailState = compositionLocalOf { mutableStateOf("") }
private var localEmailError = compositionLocalOf { mutableStateOf(false) }
private var localPhoneNumberState = compositionLocalOf { mutableStateOf("") }
private var localPhoneNumberError = compositionLocalOf { mutableStateOf(false) }
private var localPasswordState = compositionLocalOf { mutableStateOf("") }
private var localPasswordError = compositionLocalOf { mutableStateOf(false) }
private var localConfirmPasswordState = compositionLocalOf { mutableStateOf("") }
private var localConfirmPasswordError = compositionLocalOf { mutableStateOf(false) }
private var localCheckedState = compositionLocalOf { mutableStateOf(false) }

@Composable
fun RegisterScreen(navController: NavController) {
    MyApp(navController)
}

@Composable
fun MyApp(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        val name by localNameState.current
        val nameError by localNameError.current
        val email by localEmailState.current
        val emailError by localEmailError.current
        val lastName by localLastNameState.current
        val lastNameError by localLastNameError.current
        val password by localPasswordState.current
        val passwordError by localPasswordError.current
        val phoneNumber by localPhoneNumberState.current
        val phoneNumberError by localPhoneNumberError.current
        val confirmPassword by localConfirmPasswordState.current
        val confirmPasswordError by localConfirmPasswordError.current
        val checkPrivacyPolicy by localCheckedState.current
        var enableButton = false
        val padding = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)
        val roundedCornerShape = RoundedCornerShape(30.dp)

        CreateTitleAndSubtitle(padding)
        Form(padding, roundedCornerShape)

        if (name.isNotEmpty() && lastName.isNotBlank() && email.isNotBlank() &&
            phoneNumber.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() &&
            checkPrivacyPolicy
        ) {
            enableButton = (!nameError && !emailError && !lastNameError && !passwordError && !phoneNumberError && !confirmPasswordError)
        }
        ButtonRegister(
            submit = {
                click(
                    name,
                    lastName,
                    email,
                    phoneNumber,
                    password,
                    confirmPassword,
                    checkPrivacyPolicy
                )
            },
            modifier = padding,
            enableButton = enableButton
        )
    }
}

@Composable
private fun CreateTitleAndSubtitle(modifier: Modifier) {
    Text(
        text = "PetJournal",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )

    Text(
        text = "Inscreva-se",
        fontSize = 25.sp,
        modifier = modifier
    )
}

@Composable
private fun Name(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    var name by localNameState.current
    var nameError by localNameError.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = name,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray
        ),
        onValueChange = { name = it },
        label = { Text("Nome") },
        placeholder = { Text("eg: Thayna") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        isError = showErrorLenght || showErrorCharacter,
        shape = roundedCornerShape,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus)
                    it.hasFocus
                else {
                    it.hasFocus
                }
            }
    )
    if (isValidLenght(name) && !inFocus) {
        showErrorLenght = isValidLenght(name)
        AlertText(textMessage = "O Nome precisa ter entre 3 e 30 caracteres..")
    } else {
        showErrorLenght = false
    }
    if (hasSpecialCharOrNumber(name) && !inFocus) {
        showErrorCharacter = hasSpecialCharOrNumber(name)
        AlertText(textMessage = "Caracteres especiais não são permitidos!")
    } else {
        showErrorCharacter = false
    }
    nameError = hasSpecialCharOrNumber(name) || isValidLenght(name)
}

@Composable
private fun LastName(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    var lastName by localLastNameState.current
    var lastNameError by localLastNameError.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = lastName,
        onValueChange = { lastName = it },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray
        ),
        label = { Text("Sobrenome") },
        placeholder = { Text("eg: Oliveira") },
        isError = showErrorLenght || showErrorCharacter,
        shape = roundedCornerShape,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus)
                    it.hasFocus
                else {
                    it.hasFocus
                }
            }
    )
    if (isValidLenght(lastName) && !inFocus) {
        showErrorLenght = isValidLenght(lastName)
        AlertText(textMessage = "O Sobrenome precisa ter entre 3 e 30 caracteres..")
    } else {
        showErrorLenght = false
    }
    if (hasSpecialCharOrNumber(lastName) && !inFocus) {
        showErrorCharacter = hasSpecialCharOrNumber(lastName)
        AlertText(textMessage = "Caracteres especiais não são permitidos!")
    } else {
        showErrorCharacter = false

    }
    lastNameError = isValidLenght(lastName) || hasSpecialCharOrNumber(lastName)
}

@Composable
private fun Email(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    var email by localEmailState.current
    var emailError by localEmailError.current
    var inFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = email,
        onValueChange = { newEmail -> email = newEmail },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray
        ),
        label = { Text("Email") },
        placeholder = { Text("eg: exemple@petjournal.com") },
        isError = emailError,
        shape = roundedCornerShape,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus)
                    it.hasFocus
                else {
                    it.hasFocus
                }
            }
    )
    if (!inFocus && !isEmail(email) && email.isNotBlank() && (email.length) >= 1) {
        emailError = true
        AlertText(textMessage = "Forneça um email no formato correto.")
    } else {
        emailError = false
    }
}

@Composable
private fun PhoneNumber(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    var phoneNumber by localPhoneNumberState.current
    var phoneNumberError by localPhoneNumberError.current
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = phoneNumber,
        onValueChange = {
            if (it.length <= 11) phoneNumber = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = { mobileNumberFilter(it) },
        textStyle = TextStyle(fontSize = 25.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray
        ),
        label = { Text("Telefone") },
        placeholder = { Text("eg: 91 9 1234-4567") },
        isError = phoneNumberError,
        shape = roundedCornerShape,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus) it.hasFocus
                else it.hasFocus
            },
    )
    if (!inFocus && (phoneNumber.length in 1..10)) {
        phoneNumberError = true
        AlertText(textMessage = "Complete o número de telefone!")
    } else {
        phoneNumberError = false
    }
}

@Composable
private fun Password(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    var password by localPasswordState.current
    var passwordError by localPasswordError.current
    var inFocusPwd by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { newPassword -> password = newPassword },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray
        ),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {


            val (icon, iconColor) = if (showPassword) {
                Pair(
                    Icons.Filled.Visibility,
                    colorResource(id = R.color.black)
                )
            } else {
                Pair(
                    Icons.Filled.VisibilityOff,
                    colorResource(id = R.color.black)
                )
            }

            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    icon,
                    contentDescription = "Visibility",
                    tint = iconColor
                )
            }
                       },
        singleLine = true,
        label = { Text("Senha") },
        placeholder = { Text("Senha") },
        isError = passwordError,
        shape = roundedCornerShape,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocusPwd = if (it.hasFocus) it.hasFocus else it.hasFocus
            }
    )
    if (inFocusPwd) {
        val listItens = countCharacters(password)
        var count = 0
        if (listItens[0] < 2) AlertText(textMessage = "Pelo menos duas letras Maiusculas (ex: F, G, ...)") else count++
        if (listItens[1] < 2) AlertText(textMessage = "Pelo menos duas letras Minusculas (ex: f, g, ...)") else count++
        if (listItens[2] < 2) AlertText(textMessage = "Pelo menos dois Simbolos (ex: %, &, ...)") else count++
        if (listItens[3] < 2) AlertText(textMessage = "Pelo menos dois Numeros (ex: 2, 5, ...)") else count++
        passwordError = count != 4
    } else {
    }
}

@Composable
private fun ConfirmPassword(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    var password by localPasswordState.current
    var confirmPassword by localConfirmPasswordState.current
    var confirmPasswordError by localConfirmPasswordError.current
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = confirmPassword,
        onValueChange = {
            confirmPassword = it
            confirmPasswordError =
                (confirmPassword != password) && (password.isNotBlank()) && (password.isNotEmpty())
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray
        ),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val (icon, iconColor) = if (showPassword) {
                Pair(
                    Icons.Filled.Visibility,
                    colorResource(id = R.color.black)
                )
            } else {
                Pair(
                    Icons.Filled.VisibilityOff,
                    colorResource(id = R.color.black)
                )
            }

            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    icon,
                    contentDescription = "Visibility",
                    tint = iconColor
                )
            }
        },
        singleLine = true,
        isError = confirmPasswordError,
        label = { Text("Confirmar Senha") },
        placeholder = { Text("Confirmar Senha") },
        shape = roundedCornerShape,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
private fun PrivacyPolicyCheckbox() {
    var checked by localCheckedState.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 0.dp, end = 0.dp, top = 16.dp)
            .fillMaxWidth()
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                uncheckedColor = Color.Gray
            )
        )
        Text(
            text = "Eu concordo com a política de privacidade",
        )
    }
}

@Composable
private fun Form(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    Name(modifier = modifier, roundedCornerShape = roundedCornerShape)
    LastName(modifier = modifier, roundedCornerShape = roundedCornerShape)
    Email(modifier = modifier, roundedCornerShape = roundedCornerShape)
    PhoneNumber(modifier = modifier, roundedCornerShape = roundedCornerShape)
    Password(modifier = modifier, roundedCornerShape = roundedCornerShape)
    ConfirmPassword(modifier = modifier, roundedCornerShape = roundedCornerShape)
    PrivacyPolicyCheckbox()
}

@Composable
private fun ButtonRegister(submit: () -> Unit, modifier: Modifier, enableButton: Boolean) {
    Button(
        onClick = { submit() },
        enabled = enableButton,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topEnd = 20.dp, bottomStart = 20.dp)
    ) {
        Text(text = "Cadastrar", fontSize = 20.sp)
    }
}

private fun click(
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
            "testar", "$name, $lastName, $email, $phoneNumber, $password, $confirmPassword, $check"
        )
    }
}
