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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.util.*
import com.soujunior.petjournal.ui.util.mask.mobileNumberFilter

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
        val email by localEmailState.current
        val lastName by localLastNameState.current
        val password by localPasswordState.current
        val phoneNumber by localPhoneNumberState.current
        val confirmPassword by localConfirmPasswordState.current
        val checkPrivacyPolicy by localCheckedState.current
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
            checkPrivacyPolicy
        ) {
            enableButton = true
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
            modifier = paddingForm,
            enableButton = enableButton
        )
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
fun Name(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
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
        modifier = modifier.fillMaxWidth(),
        shape = roundedCornerShape,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        isError = showErrorLenght || showErrorCharacter
    )
    if (showErrorLenght) {
        AlertText(modifier, textMessage = "O Nome precisa ter entre 3 e 30 caracteres..")
    }
    if (showErrorCharacter) {
        AlertText(modifier, textMessage = "Caracteres especiais não são permitidos!")
    }
}

@Composable
fun LastName(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
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
        modifier = modifier.fillMaxWidth(),
        shape = roundedCornerShape,
        isError = showErrorLenght || showErrorCharacter
    )
    if (showErrorLenght) {
        AlertText(
            modifier,
            textMessage = "O Sobrenome precisa ter entre 3 e 30 caracteres.."
        )
    }
    if (showErrorCharacter) {
        AlertText(modifier, textMessage = "Caracteres especiais não são permitidos!")
    }
}

@Composable
fun Email(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
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
        modifier = modifier
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
        AlertText(modifier, textMessage = "Forneça um email no formato correto")
    } else {
        error = false
    }
}

@Composable
fun PhoneNumber(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    var phoneNumber by localPhoneNumberState.current
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = phoneNumber,
        onValueChange = {
            if (it.length <= 11) phoneNumber = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = { mobileNumberFilter(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        textStyle = TextStyle(fontSize = 25.sp),
        label = { Text("Phone Number") },
        placeholder = { Text("Phone Number") },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus) it.hasFocus
                else it.hasFocus
            },
        shape = roundedCornerShape,
    )
}

@Composable
fun Password(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    var password by localPasswordState.current
    var confirmPassword by localConfirmPasswordState.current
    var inFocusPwd by remember { mutableStateOf(false) }
    var inFocusConfirmPwd by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { newPassword -> password = newPassword },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Blue
        ),
        label = { Text("Senha") },
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
        if (count == 4) {
            Log.e("testar", "Esta em foco e a senha esta cumprindo os requisitos. Count: $count")
            /*TODO: Esta cumprindo todos os requisitos -> Contorno deve ser modificado?*/
        }
    } else {
        Log.e("testar", "Não esta em foco: ${inFocusPwd}")
    }
}
@Composable
fun ConfirmPassword(modifier: Modifier, roundedCornerShape: RoundedCornerShape){
    var password by localPasswordState.current
    var confirmPassword by localConfirmPasswordState.current
    var colorBorder = Color.Gray
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { newConfirmPassword ->
            confirmPassword = newConfirmPassword
            if (confirmPassword != password) {
                Log.i("testar", "diferente")
                colorBorder = Color.Gray
            } else {
                Log.i("testar", "igual")
                colorBorder = Color.Green
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorBorder,
            textColor = Color.Blue
        ),
        label = {
            Text("Confirmar Senha")
        },
        shape = roundedCornerShape,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun PrivacyPolicyCheckbox(modifier: Modifier) {
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
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Form(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {
    Name(modifier = modifier, roundedCornerShape = roundedCornerShape)
    LastName(modifier = modifier, roundedCornerShape = roundedCornerShape)
    Email(modifier = modifier, roundedCornerShape = roundedCornerShape)
    PhoneNumber(modifier = modifier, roundedCornerShape = roundedCornerShape)
    Password(modifier = modifier, roundedCornerShape = roundedCornerShape)
    ConfirmPassword(modifier = modifier, roundedCornerShape = roundedCornerShape)
    PrivacyPolicyCheckbox(modifier = modifier)
}

@Composable
fun ButtonRegister(submit: () -> Unit, modifier: Modifier, enableButton: Boolean) {
    Button(
        onClick = { submit() },
        enabled = enableButton,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topEnd = 20.dp, bottomStart = 20.dp)
    ) {
        Text(text = "Cadastrar", fontSize = 20.sp)
    }
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
    //TODO: Adicionar verificação antes da ação de click, para saber se todos os campos estão seguindo as recomendações
    if (check) {
        Log.i(
            "testar", "$name, $lastName, $email, $phoneNumber, $password, $confirmPassword, $check"
        )
    }
}