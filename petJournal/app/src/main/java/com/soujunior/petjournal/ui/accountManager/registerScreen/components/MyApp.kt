package com.soujunior.petjournal.ui.accountManager.registerScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.components.ConfirmPassword
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.Email
import com.soujunior.petjournal.ui.components.Password
import com.soujunior.petjournal.ui.components.PhoneNumber
import com.soujunior.petjournal.ui.components.PrivacyPolicyCheckbox
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterScreenViewModel
import com.soujunior.petjournal.ui.accountManager.registerScreen.postForm
import com.soujunior.petjournal.ui.accountManager.registerScreen.state.StatesRegister
import com.soujunior.petjournal.ui.states.States

@Composable
fun MyApp(navController: NavController, RegisterScreenViewModel: RegisterScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            item { CreateTitleAndImageLogo("Inscreva-se", spaceBottom = 10.dp) }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item { Name() }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item { LastName() }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item { Email() }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item { PhoneNumber() }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item { Password() }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item { ConfirmPassword() }
            item { Spacer(modifier = Modifier.height(5.dp)) }
            item { PrivacyPolicyCheckbox() }
            item { Spacer(modifier = Modifier.height(5.dp)) }
            item {
                val name by StatesRegister.localNameState.current
                val nameError by StatesRegister.localNameError.current
                val email by States.localEmailState.current
                val emailError by States.localEmailError.current
                val lastName by StatesRegister.localLastNameState.current
                val lastNameError by StatesRegister.localLastNameError.current
                val password by States.localPasswordState.current
                val passwordError by States.localPasswordError.current
                val phoneNumber by StatesRegister.localPhoneNumberState.current
                val phoneNumberError by StatesRegister.localPhoneNumberError.current
                val confirmPassword by States.localConfirmPasswordState.current
                val confirmPasswordError by States.localConfirmPasswordError.current
                val checkPrivacyPolicy by StatesRegister.localCheckedState.current
                val enableButton =
                    if (name.isNotEmpty() && lastName.isNotBlank() && email.isNotBlank() &&
                        phoneNumber.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() &&
                        checkPrivacyPolicy
                    ) {
                        (!nameError && !emailError && !lastNameError && !passwordError && !phoneNumberError && !confirmPasswordError)
                    } else {
                        false
                    }
                Button(
                    text = "Cadastrar",
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