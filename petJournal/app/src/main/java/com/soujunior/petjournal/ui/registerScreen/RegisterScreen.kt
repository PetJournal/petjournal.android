package com.soujunior.petjournal.ui.registerScreen

import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.registerScreen.components.ImageLogo
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.*
import com.soujunior.petjournal.ui.util.mask.mobileNumberFilter
import org.koin.androidx.compose.getViewModel

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
private var showPrivacyPolicy = compositionLocalOf { mutableStateOf(false) }

@Composable
fun RegisterScreen(navController: NavController) {
    MyApp(navController)
}

@Composable
fun MyApp(navController: NavController) {
    val RegisterScreenViewModel: RegisterScreenViewModel = getViewModel()

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
                val padding = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)
                CreateTitleAndImageLogo()
                Form(padding)
            }
            item {
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

                enableButton =
                    if (name.isNotEmpty() && lastName.isNotBlank() && email.isNotBlank() &&
                        phoneNumber.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() &&
                        checkPrivacyPolicy
                    ) {
                        (!nameError && !emailError && !lastNameError && !passwordError && !phoneNumberError && !confirmPasswordError)
                    } else {
                        false
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
                    enableButton = enableButton
                )
            }
        }
    }
}

@Composable
private fun CreateTitleAndImageLogo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        ImageLogo()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Inscreva-se",
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(start = 8.dp, top = 16.dp),
        )
    }
}

@Composable
private fun Name(modifier: Modifier) {
    var name by localNameState.current
    var nameError by localNameError.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        label = {
            Text(
                text = "Nome",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "eg: Thayna",
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        isError = showErrorLenght || showErrorCharacter,
        shape = Shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                inFocus = if (it.hasFocus)
                    it.hasFocus
                else {
                    it.hasFocus
                }
            },
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
private fun LastName(modifier: Modifier) {
    var lastName by localLastNameState.current
    var lastNameError by localLastNameError.current
    var showErrorLenght by remember { mutableStateOf(false) }
    var showErrorCharacter by remember { mutableStateOf(false) }
    var inFocus by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = lastName,
        onValueChange = { lastName = it },
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        label = {
            Text(
                text = "Sobrenome",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "eg: Oliveira",
                style = MaterialTheme.typography.body1
            )
        },
        isError = showErrorLenght || showErrorCharacter,
        shape = Shapes.small,
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
private fun Email(modifier: Modifier) {
    var email by localEmailState.current
    var emailError by localEmailError.current
    var inFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = email,
        onValueChange = { newEmail -> email = newEmail },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        label = {
            Text(
                text = "Email",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "eg: exemple@petjournal.com",
                style = MaterialTheme.typography.body1
            )
        },
        isError = emailError,
        shape = Shapes.small,
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
private fun PhoneNumber(modifier: Modifier) {
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
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        label = {
            Text(
                text = "Telefone",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "eg: 91 9 1234-4567",
                style = MaterialTheme.typography.body1
            )
        },
        isError = phoneNumberError,
        shape = Shapes.small,
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
private fun Password(modifier: Modifier) {
    var password by localPasswordState.current
    var passwordError by localPasswordError.current
    var inFocusPwd by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { newPassword -> password = newPassword },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        trailingIcon = {
            val (icon, iconColor) = if (showPassword) {
                Pair(
                    Icons.Filled.Visibility,
                    MaterialTheme.colors.primary
                )
            } else {
                Pair(
                    Icons.Filled.VisibilityOff,
                    MaterialTheme.colors.primary
                )
            }
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    icon,
                    contentDescription = "Modifica a visibilidade do campo senha",
                    tint = iconColor
                )
            }
        },
        singleLine = true,
        label = { Text(text = "Senha", fontFamily = FontFamily(Font(R.font.fredoka_regular))) },
        placeholder = {
            Text(
                text = "Senha",
                style = MaterialTheme.typography.body1
            )
        },
        isError = passwordError,
        shape = Shapes.small,
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
    }
}

@Composable
private fun ConfirmPassword(modifier: Modifier) {
    val password by localPasswordState.current
    var confirmPassword by localConfirmPasswordState.current
    var confirmPasswordError by localConfirmPasswordError.current
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val (icon, iconColor) = if (showPassword) {
                Pair(
                    Icons.Filled.Visibility,
                    MaterialTheme.colors.primary
                )
            } else {
                Pair(
                    Icons.Filled.VisibilityOff,
                    MaterialTheme.colors.primary
                )
            }

            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    icon,
                    contentDescription = "Modifica a visibilidade do campo confirmar senha",
                    tint = iconColor
                )
            }
        },
        singleLine = true,
        isError = confirmPasswordError,
        label = {
            Text(
                text = "Confirmar Senha",
                style = MaterialTheme.typography.body1
            )
        },
        placeholder = {
            Text(
                text = "Confirmar Senha",
                style = MaterialTheme.typography.body1
            )
        },
        shape = Shapes.small,
        modifier = modifier
            .fillMaxWidth()
    )
    if (password.isNotBlank())
        confirmPasswordError = confirmPassword != password
}

@Composable
private fun Form(modifier: Modifier) {
    Name(modifier = modifier)
    LastName(modifier = modifier)
    Email(modifier = modifier)
    PhoneNumber(modifier = modifier)
    Password(modifier = modifier)
    ConfirmPassword(modifier = modifier)
    PrivacyPolicyCheckbox()
}

@Composable
private fun ButtonRegister(submit: () -> Unit, enableButton: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { submit() },
            enabled = enableButton,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
                .size(height = 50.dp, width = 200.dp),
            shape = Shapes.large
        ) {
            Text(
                text = "Cadastrar",
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
private fun PrivacyPolicyCheckbox() {
    var showPrivacyPolicy by showPrivacyPolicy.current
    var checked by localCheckedState.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 0.dp, end = 0.dp, top = 16.dp)
            .fillMaxWidth()
    ) {
        Column {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                modifier = Modifier.clickable { }
            )
        }
        Column {
            Text(
                text = "Eu concordo com a política de privacidade",
                modifier = Modifier.clickable(onClick = { showPrivacyPolicy = true }),
                style = MaterialTheme.typography.body1
            )
        }
    }
    if (showPrivacyPolicy) {
        Dialog(
            onDismissRequest = { showPrivacyPolicy = false },
            content = { BoxWithPrivacyPolicyText() }
        )
    }
}

@Composable
fun BoxWithPrivacyPolicyText() {
    var showPrivacyPolicy by showPrivacyPolicy.current
    Column(
        modifier = Modifier
            .size(800.dp)
            .background(MaterialTheme.colors.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(
                onClick = { showPrivacyPolicy = false }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Botão para fechar a caixa"
                )
            }
        }
        LazyColumn {
            item { Text( text = stringResource(id = R.string.privacy_policy_item_0_title), style = MaterialTheme.typography.h3) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_0_text), style = MaterialTheme.typography.body1) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_1_title), style = MaterialTheme.typography.h3) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_1_text), style = MaterialTheme.typography.body1) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_2_title), style = MaterialTheme.typography.h3) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_2_text), style = MaterialTheme.typography.body1) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_3_title), style = MaterialTheme.typography.h3) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_3_text), style = MaterialTheme.typography.body1) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_4_title), style = MaterialTheme.typography.h3) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_4_text), style = MaterialTheme.typography.body1) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_5_title), style = MaterialTheme.typography.h3) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_5_text), style = MaterialTheme.typography.body1) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_6_title), style = MaterialTheme.typography.h3) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_6_text), style = MaterialTheme.typography.body1) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_7_title), style = MaterialTheme.typography.h3) }
            item { Text( text = stringResource(id = R.string.privacy_policy_item_7_text), style = MaterialTheme.typography.body1) }
        }
    }
}


private fun click(
    name: String, lastName: String, email: String, phoneNumber: String,
    password: String, confirmPassword: String, check: Boolean,
) {

    if (check) {
        Log.i(
            "testar", "$name, $lastName, $email, $phoneNumber, $password, $confirmPassword, $check"
        )
    }
}