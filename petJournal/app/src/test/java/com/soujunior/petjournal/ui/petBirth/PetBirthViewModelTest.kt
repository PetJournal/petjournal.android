package com.soujunior.petjournal.ui.petBirth

import androidx.lifecycle.viewModelScope
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.appArea.pets.petBirthScreen.BirthFormEvent
import com.soujunior.petjournal.ui.appArea.pets.petBirthScreen.BirthFormState
import com.soujunior.petjournal.ui.appArea.pets.petBirthScreen.ViewModelBirthImpl
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

class PetBirthViewModelTest {

    private lateinit var viewModelTest: ViewModelBirthImpl
    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModelTest = ViewModelBirthImpl(validation)
    }

    @After
    fun tearDown() {
        viewModelTest.viewModelScope.cancel()
    }

    @Test
    fun `enable button when of the birth date are validated`() {

        every { this@PetBirthViewModelTest.validation.validateDate(any()) } returns ValidationResult(
            success = true
        )

        viewModelTest.state = BirthFormState(
            birth = "22/10/2020",
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isTrue()
    }

    @Test
    fun `cannot enable button with empty birth date`() {
        viewModelTest.state = BirthFormState(
            birthError = listOf("Ops! Verifique se a data preenchida está correta."),
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `cannot enable button with birth date format invalid`() {
        viewModelTest.state = BirthFormState(
            birthError = listOf("Ops! Verifique se a data preenchida está correta."),
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }


    @Test
    fun `when change() is called with another birth date, should change the date`() {
        val newBirth = "22/12/2020"

        every { this@PetBirthViewModelTest.validation.validateDate(newBirth) } returns ValidationResult(
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
            this@PetBirthViewModelTest.validation.validateDate(newBirth)
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
            this@PetBirthViewModelTest.validation.validateDate(newBirth)
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
            this@PetBirthViewModelTest.validation.validateDate(newBirth)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Ops! Verifique se a data preenchida está correta.")

        )
        viewModelTest.change(petBirth = newBirth)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertNotNull(viewModelTest.state.birthError)
    }

    @Test
    fun `should not accept pet birth date with more than 30 chars`() {
        val newBirth = "Shoryukenhadoukentatsumakisenpukyaku"

        every {
            this@PetBirthViewModelTest.validation.validateDate(newBirth)
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
            this@PetBirthViewModelTest.validation.validateDate(newBirth)
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
            this@PetBirthViewModelTest.validation.validateDate(newBirth)
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
            this@PetBirthViewModelTest.validation.validateDate(newBirth)
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
        val event = BirthFormEvent.PetBirth(petBirth = newBirth)
        viewModelTest.onEvent(event)
        assertEquals(newBirth, viewModelTest.state.birth)
        assertEquals(emptyList<String>(), viewModelTest.state.birthError)
    }

}