package com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.soujunior.domain.model.response.pet_information.Breed
import com.soujunior.domain.model.response.pet_information.PetInformationItem
import com.soujunior.domain.model.response.pet_information.Size
import com.soujunior.domain.model.response.pet_information.Specie
import com.soujunior.petjournal.navigation.navHostMock
import com.soujunior.petjournal.ui.components.PetItemCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisteredPetScreenTest {

    private val list = listOf(
        PetInformationItem(
            id = "1",
            guardianId = "G001",
            specie = Specie(null, null),
            specieAlias = "Canine",
            petName = "Buddy",
            gender = "Male",
            breedAlias = "Labrador",
            breed = Breed(null, null),
            size = Size(null, null),
            castrated = true,
            dateOfBirth = "2018-04-15"
        ),
        PetInformationItem(
            id = "2",
            guardianId = "G002",
            specie = Specie(null, null),
            specieAlias = "Feline",
            petName = "Whiskers",
            gender = "Female",
            breedAlias = "Siamese",
            breed = Breed(null, null),
            size = Size(null, null),
            castrated = false,
            dateOfBirth = "2020-08-10"
        ),
        PetInformationItem(
            id = "3",
            guardianId = "G003",
            specie = Specie(null, null),
            specieAlias = "Avian",
            petName = "Sky",
            gender = "Male",
            breedAlias = "Parrot",
            breed = Breed(null, null),
            size = Size(null, null),
            castrated = null,
            dateOfBirth = "2019-03-25"
        ),
        PetInformationItem(
            id = "4",
            guardianId = "G004",
            specie = Specie(null, null),
            specieAlias = "Canine",
            petName = "Max",
            gender = "Male",
            breedAlias = "German Shepherd",
            breed = Breed(null, null),
            size = Size(null, null),
            castrated = true,
            dateOfBirth = "2017-12-05"
        ),
        PetInformationItem(
            id = "5",
            guardianId = "G005",
            specie = Specie(null, null),
            specieAlias = "Feline",
            petName = "Mittens",
            gender = "Female",
            breedAlias = "Persian",
            breed = Breed(null, null),
            size = Size(null, null),
            castrated = true,
            dateOfBirth = "2019-06-20"
        )
    )


    @get:Rule
    val composeTestRule = createComposeRule()

    private var navController = NavHostController(ApplicationProvider.getApplicationContext())

    @Test
    fun when_RegisteredPetScreen_isEmpty_should_show_message_toRegister_newPet() {
        composeTestRule.onNodeWithText("Cadastre as informações do seus pets clicando no botão abaixo")
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun when_adding_newPet_should_show_inTheList() {

        composeTestRule.setContent {
            LazyColumn(
                modifier = Modifier.testTag("ListOfPets")
            ) {
                items(
                    items = list,
                    itemContent = { item ->
                        PetItemCard(
                            item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .combinedClickable
                                    (
                                    onClick = {

                                    },
                                )
                        )


                    })
            }
        }
        composeTestRule.onNodeWithTag("ListOfPets").assertExists()
    }
}