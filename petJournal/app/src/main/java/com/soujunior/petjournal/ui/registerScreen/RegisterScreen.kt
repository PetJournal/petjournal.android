package com.soujunior.petjournal.ui.registerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.petjournal.ui.registerScreen.components.*
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val registerScreenViewModel: RegisterScreenViewModel = getViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current

    SideEffect {
        registerScreenViewModel.formSuccess.observe(lifecycleOwner) { formSuccessValue ->
            //TODO: Sucesso ->
            // Dialog com parabens
            // Chamar metodo de login logo em seguida
            // Ser encaminhado para a tela Home
            //TODO: Falha ->
            // Exibir uma mensagem avisando do erro
        }
    }


    MyApp(navController, registerScreenViewModel)
    //setupObservers(registerScreenViewModel)
}

@Composable
fun MyApp(navController: NavController, RegisterScreenViewModel: RegisterScreenViewModel) {

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
                val name by StatesRegister.localNameState.current
                val nameError by StatesRegister.localNameError.current
                val email by StatesRegister.localEmailState.current
                val emailError by StatesRegister.localEmailError.current
                val lastName by StatesRegister.localLastNameState.current
                val lastNameError by StatesRegister.localLastNameError.current
                val password by StatesRegister.localPasswordState.current
                val passwordError by StatesRegister.localPasswordError.current
                val phoneNumber by StatesRegister.localPhoneNumberState.current
                val phoneNumberError by StatesRegister.localPhoneNumberError.current
                val confirmPassword by StatesRegister.localConfirmPasswordState.current
                val confirmPasswordError by StatesRegister.localConfirmPasswordError.current
                val checkPrivacyPolicy by StatesRegister.localCheckedState.current
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
                        postForm(
                            RegisterModel(
                                name = name,
                                lastName = lastName,
                                email = email,
                                phoneNumber = phoneNumber,
                                password = password,
                                privacyPolicy = checkPrivacyPolicy
                            ),
                            RegisterScreenViewModel
                        )
                    },
                    enableButton = enableButton
                )
            }
        }
    }
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

private fun postForm(form: RegisterModel, RegisterScreenViewModel: RegisterScreenViewModel) {
    if (form.privacyPolicy) {
        RegisterScreenViewModel.postForm(form)
    }
}