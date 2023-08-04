package com.soujunior.petjournal.setup

import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.RegisterModel

val formLogin  = LoginModel (
    "fulano@email.com",
    "88@#GGas"
)

val formRegister  = RegisterModel (
    firstName = "fulano",
    lastName = "silva",
    phone = "12345678987",
    email = "fulano@email.com",
    password = "88@#GGas",
    passwordConfirmation = "88@#GGas",
    isPrivacyPolicyAccepted = true
)

val formForgot  = ForgotPasswordModel (
    email = "fulano@email.com"
)

val sendCode = AwaitingCodeModel (
    codeOTP = "123456",
    email = "testeunitario@gmail.com",
)

