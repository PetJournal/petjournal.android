package com.soujunior.petjournal.setup

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
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

val listDogRaces = listOf(
    PetRaceItemModel(
        "a05457eb-dbca-4f02-8489-102d9b78fec3",
        "Chihuahua",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "e1228e30-229d-4a46-ae8f-66e1a9c679d9",
        "Cocker Spaniel Americano",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "ec58d9fd-795d-4fa2-b6a7-96f24a1509db",
        "Mastife",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "6435ad87-8915-4c24-9adb-2351ffad3cb6",
        "Cane Corso",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "e033d88f-e438-492f-b94f-6e4e8bb2d638",
        "Galgo Irlandês",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "a44a9201-6a0c-4869-8ebf-8191df042d94",
        "Boston Terrier",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "291e9c63-26bf-4ff3-9d4d-4758dd6ddd4c",
        "Afghan Hound",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "805ef695-2274-45e4-9273-86e098e5fa09",
        "Bull Terrier",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "b4d835bb-95cd-4d8c-a40a-2b420fbd1be3",
        "Borzoi",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "e9371479-6654-478d-aaca-85ddcabf7a95",
        "Rottweiler",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "e214aa8f-76e9-40ce-85c9-388d8df63483",
        "Outra raça Cachorro",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "4d38a1ce-91bd-486f-a022-0882948c214d",
        "Dogo Argentino",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "be96e9b6-3e52-4d79-97a6-558850e56eef",
        "Cão de Água Português",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "57f8f52f-db1e-4f0a-882c-cd18617332db",
        "Galgo Escocês",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "c45021d5-df56-431b-b66d-ec6372a7f5e1",
        "Border Terrier",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "b4696914-fd36-49f0-9b9e-9d07dbcda564",
        "Golden Retriever",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "a37b14c2-00c1-4636-9ae8-1d63c10deffd",
        "Skye Terrier",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "29297227-b000-4207-adfb-0ddcc4e3c6a5",
        "Bobtail",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "b0be9291-2ece-473c-ab94-fc96c3500102",
        "Bloodhound",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "d1ce38f7-878b-4e60-851b-ffc97710379d",
        "Malamute do Alasca",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "85a26d83-5d59-4b47-9df9-3452d266d16e",
        "Boiadeiro Bernês",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "9c31a1a3-61d2-43ac-9aa3-a4a87077f11c",
        "Pinscher Miniatura",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "e5289d78-ad38-4481-8735-0ddca5a617aa",
        "Chesapeake Bay Retriever",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "6fde0dd9-9cbc-465d-aba8-e7ee28835e7a",
        "Beagle",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "5395420c-929b-47b9-9770-4f6a220d85b5",
        "American Staffordshire Terrier",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "06b9d332-5c31-48da-b161-88bcdaff38d0",
        "Grande Boiadeiro Suiço",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "14499f71-7cf1-483a-9f7f-50db643ea303",
        "Buldogue Inglês",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "6a05c228-b2a0-4c25-b3af-3966eec08dd8",
        "Boxer",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "2cc02a9e-06e3-455a-a679-1ead616648d1",
        "Cairn Terrier",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "a54d4a6a-8711-49aa-b0df-2c1c853627e8",
        "Cão de Crista Chinês",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel(
        "bd5e1546-d7cc-43bf-98bb-297dedffd11d",
        "Maltês",
        "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetRaceItemModel("8c37b51c-5957-46dc-8877-ad62b51420a3", "Beagle Harrier", "2dac1e0")
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

val listPetSizesDog = listOf(
    PetSizeItemModel(
        id = "5b5bbd95-dfed-4423-be32-2e1be8555938",
        name = "Médio (15 à 24Kg)",
        specieId = "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetSizeItemModel(
        id = "655adc19-d163-4c38-aa8c-49a7513a67a3",
        name = "Pequeno (6 à 14Kg)",
        specieId = "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetSizeItemModel(
        id = "5e7ea2b5-24f3-4eca-80b6-7dd91734dff6",
        name = "Mini (Até 6Kg)",
        specieId = "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    ),
    PetSizeItemModel(
        id = "66178e06-cb65-422a-8786-d2038a52f3f3",
        name = "Gigante (Acima de 45Kg)",
        specieId = "2dac1e0a-5cd7-4e93-924c-0c2fe36653dc"
    )
)
val listPetSizesCat = listOf(
    PetSizeItemModel(
        id = "649a616b-3b02-4d47-95c0-dd7224760f01",
        name = "Grande (25 à 45Kg)",
        specieId = "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
    PetSizeItemModel(
        id = "fa6a7d4c-f93d-44c5-9c14-42dbf4bf16b4",
        name = "Médio (11 à 24Kg)",
        specieId = "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    ),
    PetSizeItemModel(
        id = "35ca78d5-f012-4048-9334-6836a3e82ecc",
        name = "Pequeno (Até 10Kg)",
        specieId = "a0f385f5-c0b4-4f85-8d06-cf2a4f698f93"
    )
)

