package com.soujunior.petjournal.ui.petNameAndGender

import androidx.lifecycle.viewModelScope
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.NameGenderFormEvent
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.NameGenderFormState
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.ViewModelNameGenderImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class PetNameViewModelTest {

    private lateinit var viewModelTest: ViewModelNameGenderImpl
    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)

    @Before
    fun setup(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModelTest = ViewModelNameGenderImpl(validation)
    }

    @After
    fun tearDown(){
        viewModelTest.viewModelScope.cancel()
    }

    @Test
    fun `enable button when all of the data are validated`(){
        //usado para simular um comportamento a ser mockado
        every { this@PetNameViewModelTest.validation.inputPetName(any()) } returns ValidationResult(
            success = true
        )

        every { this@PetNameViewModelTest.validation.inputPetGender(any()) } returns ValidationResult(
            success = true
        )

        viewModelTest.state = NameGenderFormState(
            name = "Bolinha",
            gender = "M",
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isTrue()
    }

    @Test
    fun `cannot enable button with empty name`(){
        viewModelTest.state = NameGenderFormState(
            name = "",
            gender = "M",
            nameError = listOf("Erro")
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `cannot enable button with empty gender`(){
        viewModelTest.state = NameGenderFormState(
            name = "Bolinha",
            gender = "",
            nameError = listOf("Erro de nome")

        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `when change() is called with another name, should change the name`(){
        val newName = "Stone Cold Steve Austin"

        every{ this@PetNameViewModelTest.validation.inputPetName(newName)} returns ValidationResult(
            success = true
        )

        viewModelTest.change(petName = newName)
        assertEquals(newName, viewModelTest.state.name)
        assertEquals(null, viewModelTest.state.nameError)
    }

    @Test
    fun `when change() is called with another gender, should change the gender`(){
        val newGender = "F"

        every { this@PetNameViewModelTest.validation.inputPetGender(newGender)
        } returns ValidationResult(
            success = true,
        )

        viewModelTest.change(petGender = newGender)
        assertEquals(newGender, viewModelTest.state.gender)
        assertEquals(null, viewModelTest.state.genderError)
    }

    @Test
    fun `should not accept pet name with special char`(){
        val newName = "Bolin#@s"

        every { this@PetNameViewModelTest.validation.inputPetName(newName)
        }returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petName = newName)
        assertEquals(newName, viewModelTest.state.name)
        assertNotNull(viewModelTest.state.nameError)
    }
    @Test
    fun `should not accept empty pet name`(){
        val newName = ""

        every { this@PetNameViewModelTest.validation.inputPetName(newName)
        }returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petName = newName)
        assertEquals(newName, viewModelTest.state.name)
        assertNotNull(viewModelTest.state.nameError)
    }

    @Test
    fun `should not accept pet name with less than 2 chars`(){
        val newName = "A"

        every { this@PetNameViewModelTest.validation.inputPetName(newName)
        }returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petName = newName)
        assertEquals(newName, viewModelTest.state.name)
        assertNotNull(viewModelTest.state.nameError)
    }

    @Test
    fun `should not accept pet name with more than 30 chars`(){
        val newName = "Shoryukenhadoukentatsumakisenpukyaku"

        every { this@PetNameViewModelTest.validation.inputPetName(newName)
        }returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petName = newName)
        assertEquals(newName, viewModelTest.state.name)
        assertNotNull(viewModelTest.state.nameError)
    }
    @Test
    fun `should accept pet name with number`(){
        val newName = "Sc0rp10n"

        every {
            this@PetNameViewModelTest.validation.inputPetName(newName)
        }returns ValidationResult(
            success = true
        )
        viewModelTest.change(petName = newName)
        assertEquals(newName, viewModelTest.state.name)
        assertEquals(null, viewModelTest.state.nameError)
    }

    @Test
    fun `should not accept other than male or female genders`(){
        val newGender = "X"

        every { this@PetNameViewModelTest.validation.inputPetGender(newGender)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")
        )

        viewModelTest.change(petGender = newGender)
        assertEquals(newGender, viewModelTest.state.gender)
        assertNotNull(viewModelTest.state.genderError)
    }

    @Test
    fun `OnEvent should allow pet name changes`(){
        val newName = "Blastoise"
        val event = NameGenderFormEvent.PetName(petName = newName)
        viewModelTest.onEvent(event)
        assertEquals(newName, viewModelTest.state.name)
        assertEquals(emptyList<String>(), viewModelTest.state.nameError)
    }

    @Test
    fun `OnEvent should allow pet gender changes`(){
        val newGender = "F"
        val event = NameGenderFormEvent.PetGender(petGender = newGender)
        viewModelTest.onEvent(event)
        assertEquals(newGender, viewModelTest.state.gender)
        assertEquals(emptyList<String>(), viewModelTest.state.genderError)
    }
}