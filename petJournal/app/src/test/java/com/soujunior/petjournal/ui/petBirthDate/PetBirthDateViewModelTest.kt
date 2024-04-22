package com.soujunior.petjournal.ui.petBirthDate

import androidx.lifecycle.viewModelScope
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.soujunior.domain.use_case.base.DataResult
import com.soujunior.domain.use_case.pet.GetPetInformationUseCase
import com.soujunior.domain.use_case.pet.UpdatePetInformationUseCase
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.setup.perInformation
import com.soujunior.petjournal.ui.screens_app.pets_screens.petBirthDateScreen.BirthDateFormEvent
import com.soujunior.petjournal.ui.screens_app.pets_screens.petBirthDateScreen.BirthDateFormState
import com.soujunior.petjournal.ui.screens_app.pets_screens.petBirthDateScreen.BirthDateViewModelImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class PetBirthDateViewModelTest {

    private lateinit var viewModelTest: BirthDateViewModelImpl
    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)
    private val getPetInformationUseCase = mockk<GetPetInformationUseCase>(relaxed = true)
    private val updatePetInformationUseCase = mockk<UpdatePetInformationUseCase>(relaxed = true)
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModelTest = BirthDateViewModelImpl(validation, getPetInformationUseCase, updatePetInformationUseCase)
    }

    @After
    fun tearDown() {
        viewModelTest.viewModelScope.cancel()
    }

    @Test
    fun `enable button when of the birth date are validated`() {

        every { this@PetBirthDateViewModelTest.validation.validateDate(any()) } returns ValidationResult(
            success = true
        )

        viewModelTest.state = BirthDateFormState(
            birth = "22/10/2020",
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isTrue()
    }

    @Test
    fun `cannot enable button with empty birth date`() {
        viewModelTest.state = BirthDateFormState(
            birthError = listOf("Ops! Verifique se a data preenchida está correta."),
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `cannot enable button with birth date format invalid`() {
        viewModelTest.state = BirthDateFormState(
            birthError = listOf("Ops! Verifique se a data preenchida está correta."),
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }


    @Test
    fun `when change() is called with another birth date, should change the date`() {
        val newBirth = "22/12/2020"

        every { this@PetBirthDateViewModelTest.validation.validateDate(newBirth) } returns ValidationResult(
            success = true
        )

        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertEquals(null, viewModelTest.state.birthError)
    }

    @Test
    fun `should not accept pet birth date with char`() {
        val newBirth = "abcdefghijklmnopqrstwxyz"

        every {
            this@PetBirthDateViewModelTest.validation.validateDate(newBirth)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Ops! Verifique se a data preenchida está correta.")

        )
        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertNotNull(viewModelTest.state.birthError)
    }

    @Test
    fun `cannot accept empty birth date`() {
        val newBirth = ""

        every {
            this@PetBirthDateViewModelTest.validation.validateDate(newBirth)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Ops! Verifique se a data preenchida está correta.")

        )
        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertNotNull(viewModelTest.state.birthError)
    }

    @Test
    fun `should not accept pet birth date with less than 8 chars`() {
        val newBirth = "22/12/20"

        every {
            this@PetBirthDateViewModelTest.validation.validateDate(newBirth)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Ops! Verifique se a data preenchida está correta.")

        )
        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertNotNull(viewModelTest.state.birthError)
    }

    @Test
    fun `should not accept pet birth date with more than 8 chars`() {
        val newBirth = "22/02/20220"

        every {
            this@PetBirthDateViewModelTest.validation.validateDate(newBirth)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Ops! Verifique se a data preenchida está correta.")

        )
        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertNotNull(viewModelTest.state.birthError)
    }

    @Test
    fun `should not accept the pet's birth date after the current date in the system`() {
        val newBirth = "22/12/2028"

        every {
            this@PetBirthDateViewModelTest.validation.validateDate(newBirth)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Ops! Verifique se a data preenchida está correta.")

        )
        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertNotNull(viewModelTest.state.birthError)
    }

    @Test
    fun `should not accept the pet's birth date with birth date before 1993`() {
        val newBirth = "01/01/1993"

        every {
            this@PetBirthDateViewModelTest.validation.validateDate(newBirth)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("A data não pode ser anterior 1993.")

        )
        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertNotNull(viewModelTest.state.birthError)
    }

    @Test
    fun `must accept a valid date of birth of the pet`() {
        val newBirth = "22/10/2010"

        every {
            this@PetBirthDateViewModelTest.validation.validateDate(newBirth)
        } returns ValidationResult(
            success = true
        )
        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertEquals(null, viewModelTest.state.birthError)
    }

    @Test
    fun `OnEvent should allow pet birth date changes`() {
        val newBirth = "22/10/2019"
        val event = BirthDateFormEvent.PetBirthDate(petBirth = newBirth)
        viewModelTest.onEvent(event)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertEquals(emptyList<String>(), viewModelTest.state.birthError)
    }
    @Test
    fun `getPetInformation should call the use case and fill the ViewModel state fields with data from the room`() {

        coEvery { getPetInformationUseCase.execute(any()) } returns DataResult.Success(
            perInformation
        )
        viewModelTest.getPetInformation(perInformation.id)

        assertEquals(perInformation.name, viewModelTest.state.name)
        assertEquals(perInformation.gender, viewModelTest.state.gender)
        assertEquals(perInformation.id, viewModelTest.state.idPetInformation)
        assertEquals(perInformation.species, viewModelTest.state.specie)
        assertEquals(perInformation.petRace, viewModelTest.state.race)
        assertEquals(perInformation.size, viewModelTest.state.size)
        assertEquals("Sucesso", viewModelTest.message.value)
    }
    @Test
    fun `should return error message if petInformation retrieval from room fails`() {

        coEvery { getPetInformationUseCase.execute(any()) } returns DataResult.Failure(
            Throwable()
        )
        viewModelTest.getPetInformation(perInformation.id)
        assertEquals("Error", viewModelTest.message.value)
    }
    @Test
    fun `updatePetInformation should call the use case to update pet information in the room`() {

        coEvery { updatePetInformationUseCase.execute(any()) } returns DataResult.Success(
            Unit
        )
        viewModelTest.updatePetInformation()
        assertEquals("Sucesso", viewModelTest.message.value)
    }
    @Test
    fun `update should return error message if pet information is not updated`() {

        coEvery { updatePetInformationUseCase.execute(any()) } returns DataResult.Failure(
            Throwable()
        )
        viewModelTest.updatePetInformation()
        assertEquals("Error", viewModelTest.message.value)
    }

}