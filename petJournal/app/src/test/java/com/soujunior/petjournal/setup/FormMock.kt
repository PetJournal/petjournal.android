package com.soujunior.petjournal.setup

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.SignUpModel

val formLogin = LoginModel(
    "fulano@email.com",
    "88@#GGas"
)

val formRegister = SignUpModel(
    firstName = "fulano",
    lastName = "silva",
    phone = "12345678987",
    email = "fulano@email.com",
    password = "88@#GGas",
    passwordConfirmation = "88@#GGas",
    isPrivacyPolicyAccepted = true
)

val formForgot = ForgotPasswordModel(
    email = "fulano@email.com"
)

val sendCode = AwaitingCodeModel(
    email = "testeunitario@gmail.com",
    verificationToken = "123456",
)

val perInformation = PetInformationModel(
    id = 1,
    species = "Dog",
    name = "Bolinha",
    gender = "M",
    size = "Pequeno",
    petRace = "Akita",
    petAge = "12122012",
    guardianId = 1,
    castration = true
)

