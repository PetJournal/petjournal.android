package com.soujunior.domain.setup

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.SignUpModel

val formLogin  = LoginModel (
    "fulano@email.com",
    "88@#GGas"
)

val formRegister  = SignUpModel (
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

val petInformation = PetInformationModel(
    id = 1,
    species = "Dog",
    guardianId = 1
)
