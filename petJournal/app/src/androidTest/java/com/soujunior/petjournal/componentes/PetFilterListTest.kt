package com.soujunior.petjournal.componentes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.compose.ui.res.painterResource
import com.soujunior.petjournal.R // Para carregar imagens de preview
import com.soujunior.petjournal.ui.components.PetFilterList
import com.soujunior.petjournal.ui.components.Pets
import io.mockk.mockk
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class PetFilterListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockOnSelectedPet: (String) -> Unit = mockk(relaxed = true)

    private val dummyPetList = listOf(
        Pets(
            id = 1,
            imageRes = null,
            name = "Jujuba"
        ),
        Pets(
            id = 2,
            imageRes = null,
            name = "Alfredo"
        )
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            val listWithPainters = dummyPetList.map {
                it.copy(imageRes = painterResource(id = R.drawable.penguim))
            }

            PetFilterList(
                listPet = listWithPainters,
                onSelectedPet = mockOnSelectedPet
            )
        }
    }

    @Test
    fun test_PetFilterList_DisplaysItems() {
        composeTestRule.onNodeWithTag("PetItem_Todos").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PetItem_Jujuba").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PetItem_Alfredo").assertIsDisplayed()
    }

    @Test
    fun test_PetFilterList_SelectsAndDeselectsPet() {
        val jujubaSelectedIconMatcher =
            hasParent(hasTestTag("PetItem_Jujuba")) and hasTestTag("SelectedIcon")

        composeTestRule.onNode(jujubaSelectedIconMatcher).assertDoesNotExist()

        composeTestRule.onNodeWithTag("PetItem_Jujuba").performClick()

        composeTestRule.onNode(jujubaSelectedIconMatcher).assertIsDisplayed()

        composeTestRule.onNodeWithTag("PetItem_Jujuba").performClick()

        composeTestRule.onNode(jujubaSelectedIconMatcher).assertDoesNotExist()
    }

    @Test
    fun test_PetFilterList_CallbackIsCalledOnSelection() {
        composeTestRule.onNodeWithTag("PetItem_Alfredo").performClick()

        verify { mockOnSelectedPet("Alfredo") }

        composeTestRule.onNodeWithTag("PetItem_Todos").performClick()

        verify { mockOnSelectedPet("Todos") }
    }
}