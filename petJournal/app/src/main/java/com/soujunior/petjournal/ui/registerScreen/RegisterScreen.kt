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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
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
        Image(
            painter = painterResource(id = R.drawable.logo_pet_journal_white),
            contentDescription = "Imagem logo",
            modifier = Modifier
                .size(width = 150.dp, height = 150.dp)
                .padding(top = 20.dp)
        )
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
                    contentDescription = "Visibility",
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
                    contentDescription = "Visibility",
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
            item { Doc_one() }
            item { Doc_two() }
            item { Doc_three() }
        }
    }
}

@Composable
fun Doc_one() {
    AndroidView(
        factory = { context -> TextView(context) },
        update = {
            it.text = HtmlCompat.fromHtml(
                """
                  <html>
                    <b>Política de privacidade</b>
                    <br>
                    <p style="text-align: justify">
                    O site PetJournal é de propriedade da empresa SouJunior Labs, que é a controladora 
                    de seus dados pessoais.
                    </p>
                    <p style="text-align: justify">
                    Nós adotamos esta Política de Privacidade, que determina como nós estamos 
                    processando as informações coletadas pelo site PetJournal e também explica por 
                    quais razões nós precisamos coletar dados pessoais sobre você. Portanto, você 
                    deve ler esta Política de Privacidade antes de usar o site PetJournal .
                    </p>
                    <p style="text-align: justify">
                    Nós cuidamos dos seus dados pessoais e assumimos a responsabilidade de garantir 
                    a confidencialidade e segurança de suas informações pessiais.
                    </p>
                    <br>
                    <b>Estes são as informações pessoais que coletamos:</b>
                    <p style="text-align: justify">
                    Quando você visita o site PetJournal , nós automaticamente coletamos certas 
                    informações sobre seu dispositivo, incluindo informações sobre seu navegador, 
                    endereço IP, fuso horário e alguns dos cookies instalados no seu sispositivo. 
                    Além disso, quando você navega pelo Site, nós coletamos informações sobre as 
                    páginas individuais ou produtos que você visualiza, sobre quais sites ou termos 
                    de busca redirecionaram você para nosso Site, e sobre como você interage com o Site. 
                    Nós nos referimos a essas informações coletadas automaticamente como "Informações 
                    sobre o dispositivo". Além disso, nós podemos coletar dados pessoais que você 
                    fornecer (incluindo, mas não limitado a: Nome, Sobrenome, Endereço, informações 
                    de pagamento, etc) durante o processo de registro para poder cumprir o acordo.
                    </p>
                    <br>
                    <b>Por que fazemos o processamento dos seus dados?</b>
                    <p style="text-align: justify">
                    Nossa maior prioridade é a segurança dos dados pessoais dos usuários e, portanto, 
                    nós podemos processar apenas dados mínimos, apenas enquanto for absolutamente 
                    necessário para a manutenção do site. Informações coletadas automaticamente são 
                    usadas para identificar possíveis casos de abuso e estabelecer dados estatísticos 
                    sobre o uso do site. Esses dados estatísticos não pe agregada de outras formas que 
                    permitam a identificação de usuários específicos do sistema.
                    </p>
                    <p style="text-align: justify">
                    Você pode visitar o site sem nos contar sobre quem você é ou relevar qualquer 
                    informação que possa ser usada por outra pessoa para identificar você individualmente. 
                    Se, apesar disso, você quiser utilizar algum dos recursos do site, ou quiser receber 
                    nossa newsletter, ou quiser conceder outros detalhes através do preencimento e 
                    envio de formulários, você poderá fornecer dados pessoais para nós, como seu email, 
                    nome, sobrenome, cidade de residência, organização e número de telefone. Você pode 
                    escolher não fornecer dados pessoais para nós, mas, dessa forma, talvez você não 
                    consiga usar alguns dos resursos do site.
                    </p>
                  </html>  
                """.trimIndent(),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
    )
}

@Composable
fun Doc_two() {
    AndroidView(
        factory = { context -> TextView(context) },
        update = {
            it.text = HtmlCompat.fromHtml(
                """
                <html>
                    <p style="text-align: justify">
                    Por exemplo, você não vai conseguir receber nossa Newsletter ou entrar em contato 
                    conosco diretamente pelo nosso site. Usuários que não tenham certeza sobre quais 
                    informações pessoais são obrigatórias são convidados a entrarem em contato conosco 
                    pelo e-mail joaosilvavictor@hotmail.com.br.
                    </p>
                    
                    <br>
                    <b>Seus direitos:</b>
                    <p style="text-align: justify">
                    Se você morar na Europa, estes são os direitos 
                    garantidos quando aos seus dados pessoais:
                    </p>
                    <ul>
                      <li>O direito de ser informado.</li>
                      <li>O direito ao acesso.</li>
                      <li>O direito à retificação.</li>
                      <li>O direito de deletar.</li>
                      <li>O direito de restringir o processamento.</li>
                      <li>O direito da portabilidade de dados.</li>
                      <li>O direito à objeção.</li>
                      <li>Direitos em relação a tomadas de decisão automáticas e à perfilagem.</li>
                    </ul>
        
                    <p style="text-align: justify">
                    Se você quiser exercitar esses direitos, por favor
                    entre em contato conosco usando os dados de contato abaixo.
                    </p>
                    <p style="text-align: justify">
                    Adicionalmente, se você mora na Europa, nós afirmamos que estamos processando 
                    suas informações com a finalidade de cumprir contratos que possamos ter firmado 
                    com você (por exemplo, se você fizer uma compra no nosso Site), ou para excercer 
                    os interesses legítimos da nossa empresa listados acima. Além disso, por favor 
                    saiba que suas informações poder ser transferidas para fora da Europa, incluindo 
                    para o Canadá e os Estados Unidos da América.
                    </p>
        
                    <br>
                    <b>Links para outros sites:</b>
                    <p style="text-align: justify">
                    Nosso site pode conter links para outros sites que não são controlados por nós 
                    e/ou não são de nossa propriedade. Por favor esteja ciente de que nós não somos 
                    responsáveis pelas políticas de privacidade de tais sites e organizações terceiras. 
                    Nós incentivamos você a estar ciente de quando sair do nosso site, e também 
                    incentivamos você a ler a política de privacidade de cada um dos sites que podem 
                    coletar suas informações pessoais.
                    </p>
        
                    <br>
                    <b>Segurança das informações:</b>
                    <p style="text-align: justify">Nós garantimos que as informações que você fornece 
                    estão em servidores e computadores armazenados em ambientes seguros e controlados, 
                    protegidos de acessos, usos e divulgações não-autorizadas. Nós matemos medidas de 
                    segurança administrativas, técnicas e físicas razoáveis, com finalidade de proteger 
                    os dados pessoais sob nossa custódia de de acessos, usos, modificações e divulgações 
                    não-autorizadas. Apesar disso, nenhuma transmissão de dados pela Internet ou por 
                    sistemas sem fio pode ser garantida.
                    </p>
                    <br>
                </html>
                """.trimIndent(),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
    )
}

@Composable
fun Doc_three() {
    AndroidView(
        factory = { context -> TextView(context) },
        update = {
            it.text = HtmlCompat.fromHtml(
                """ <html> 
                    <b>Declaração legal:</b>
                    <p style="text-align: justify">
                    Nós vamos divulgar qualquer informação que coletarmos, usarmos ou recebermos caso 
                    tal divulgação seja solicitada ou permitida por lei, de forma a cumprir intimações 
                    ou processos judiciais similares, e também quando considerarmos em boa fé que a 
                    divulgação é necessária para a proteção de nossos direitos, para a proteção da 
                    segurança de outros, para investigações de fraude ou para responder a uma 
                    solicitação do governo.
                    </p>

                    <br>
                    <b>Informações de contato:</b>
                    <p style="text-align: justify">
                    Se você quiser entrar em contato conosco para 
                    saber mais sobre esta Política de Privacidade, ou quiser acessar quaisquer 
                    informações relativas aos seus direitos individuais e às suas Informações 
                    Pessoais, você poderá enviar um e-mail para o endereço joaosilvavictor@hotmail.com.br.
                    </p>
                </html>
                """.trimIndent(), HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
    )
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