package com.soujunior.domain.setup

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.PetRaceItemModel
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

val listCatsRace = listOf(
    PetRaceItemModel(
        "99c18d37-d7ba-4e53-88c8-eddbe82f0063",
        "Curl Americano de Pelo Longo",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
    PetRaceItemModel(
        "eed1adba-dfb1-4b0d-a4bc-5cd64a13b4a5",
        "Siamês",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
    PetRaceItemModel(
        "dc60facc-17a6-4db5-a85b-fcb32bcb0ea0",
        "Khao Manee",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
    PetRaceItemModel(
        "93d619b2-0e0c-4a87-8099-8f2edc38363a",
        "Doméstico de Pelo Longo",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
    PetRaceItemModel(
        "ac18cda1-ead9-402b-8abf-24b76e271b5b",
        "Ashera",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
    PetRaceItemModel(
        "c553a6d5-5956-4176-8a15-4c51d9ddf523",
        "Chartreux",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
    PetRaceItemModel(
        "7bdcf272-d1f1-4419-bb9b-4c7afb7f4f5e",
        "Mekong Bobtail",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
)
