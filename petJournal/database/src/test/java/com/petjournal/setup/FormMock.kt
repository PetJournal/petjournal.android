package com.petjournal.setup

import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.PetInformation
import com.petjournal.database.database.entity.PetRace
import com.petjournal.database.database.entity.PetSize

const val DOG = "Dog"
const val CAT = "Cat"

val profile = GuardianProfile(
    firstName = null
)
val petInformation = PetInformation(
    species = DOG,
    guardianId = 1
)
val newPetInformation = PetInformation(
    id = 2,
    species = CAT,
    name = "Bolinha",
    gender = "F",
    size = "Pequeno",
    petRace = "Akita",
    petAge = "10/10/2010",
    guardianId = 1
)


val listPetRace = listOf(
    PetRace(
        "99c18d37-d7ba-4e53-88c8-eddbe82f0063",
        "Curl Americano de Pelo Longo",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    ),
    PetRace(
        "eed1adba-dfb1-4b0d-a4bc-5cd64a13b4a5",
        "Siamês",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    ),
    PetRace(
        "dc60facc-17a6-4db5-a85b-fcb32bcb0ea0",
        "Khao Manee",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    ),
    PetRace(
        "93d619b2-0e0c-4a87-8099-8f2edc38363a",
        "Doméstico de Pelo Longo",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    ),
    PetRace(
        "ac18cda1-ead9-402b-8abf-24b76e271b5b",
        "Ashera",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    ),
    PetRace(
        "c553a6d5-5956-4176-8a15-4c51d9ddf523",
        "Chartreux",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    ),
    PetRace(
        "7bdcf272-d1f1-4419-bb9b-4c7afb7f4f5e",
        "Mekong Bobtail",
        "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    )
)

val listPetSizes = listOf(
    PetSize(
        id = "649a616b-3b02-4d47-95c0-dd7224760f01",
        name = "Grande (25 à 45Kg)",
        specieId = "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    ),
    PetSize(
        id = "fa6a7d4c-f93d-44c5-9c14-42dbf4bf16b4",
        name = "Médio (11 à 24Kg)",
        specieId = "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    ),
    PetSize(
        id = "35ca78d5-f012-4048-9334-6836a3e82ecc",
        name = "Pequeno (Até 10Kg)",
        specieId = "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93",
        tag = "Cat"
    )
)

