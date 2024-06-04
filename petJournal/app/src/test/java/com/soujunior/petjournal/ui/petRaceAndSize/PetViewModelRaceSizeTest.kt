package com.soujunior.petjournal.ui.petRaceAndSize

import androidx.lifecycle.viewModelScope
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.soujunior.domain.use_case.base.DataResult
import com.soujunior.domain.use_case.pet.GetListPetRacesUseCase
import com.soujunior.domain.use_case.pet.GetListPetSizesUseCase
import com.soujunior.domain.use_case.pet.GetPetInformationUseCase
import com.soujunior.domain.use_case.pet.UpdatePetInformationUseCase
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.setup.listCatsRace
import com.soujunior.petjournal.setup.listDogRaces
import com.soujunior.petjournal.setup.listPetSizesCat
import com.soujunior.petjournal.setup.listPetSizesDog
import com.soujunior.petjournal.setup.perInformation
import com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.RaceSizeFormEvent
import com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.RaceSizeFormState
import com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.ViewModelRaceSizeImpl
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

class PetViewModelRaceSizeTest {

    private lateinit var viewModelTest: ViewModelRaceSizeImpl
    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)
    private val getPetInformationUseCase = mockk<GetPetInformationUseCase>(relaxed = true)
    private val updatePetInformationUseCase = mockk<UpdatePetInformationUseCase>(relaxed = true)
    private val getListPetSizesUseCase = mockk<GetListPetSizesUseCase>(relaxed = true)
    private val getListPetRacesUseCase = mockk<GetListPetRacesUseCase>(relaxed = true)


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModelTest =
            ViewModelRaceSizeImpl(validation, getPetInformationUseCase, updatePetInformationUseCase, getListPetSizesUseCase, getListPetRacesUseCase)
    }

    @After
    fun tearDown() {
        viewModelTest.viewModelScope.cancel()
    }


    @Test
    fun `enable button when size and race data is validated`() {
        every {
            this@PetViewModelRaceSizeTest.validation.validateDropdown(
                any(),
                any()
            )
        } returns ValidationResult(
            success = true
        )
        every {
            this@PetViewModelRaceSizeTest.validation.validateDropdown(
                any(),
                any()
            )
        } returns ValidationResult(
            success = true
        )
        viewModelTest.state = RaceSizeFormState(
            size = "Pequeno (até 10kg)",
            race = "Akita",
            raceOthers = ""
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isTrue()
    }

    @Test
    fun `enable button when other size and race data are validated and race has other value selected`() {
        every {
            this@PetViewModelRaceSizeTest.validation.validateDropdown(
                any(),
                any()
            )
        } returns ValidationResult(
            success = true
        )
        every { this@PetViewModelRaceSizeTest.validation.inputPetName(any()) } returns ValidationResult(
            success = true
        )

        viewModelTest.state = RaceSizeFormState(
            size = "Pequeno (até 10kg)",
            race = "outro",
            raceOthers = "Outra Raça"
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isTrue()
    }

    @Test
    fun `enable race others when race is other`() {
        every {
            this@PetViewModelRaceSizeTest.validation.validateDropDownRaceOthers(any())
        } returns ValidationResult(
            success = true
        )
        val enableRaceOthers = viewModelTest.enableRaceOthers()


        assertEquals(enableRaceOthers, true)
        assertEquals(null, viewModelTest.state.sizeError)
    }

    @Test
    fun `cannot enable button with error size`() {
        viewModelTest.state = RaceSizeFormState(
            sizeError = listOf("Tamanho inválido"),
            raceError = listOf(),
            raceOthersError = listOf()
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `can't enable button with error race`() {
        viewModelTest.state = RaceSizeFormState(
            sizeError = listOf(),
            raceError = listOf("Raça inválida"),
            raceOthersError = listOf()
        )
        viewModelTest.state = viewModelTest.state.copy(specie = "Dog")
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `cannot enable button with errors in fields`() {
        viewModelTest.state = RaceSizeFormState(
            size = "Médio (11 à 24kg)",
            race = "Outro",
            raceOthers = "Pastor",
            sizeError = listOf("Tamanho inválido"),
            raceError = listOf("Raça inválida"),
            raceOthersError = listOf("Raça Outro inválido")
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `when change() is called with another size, it should change the size if it is in the list`() {
        val newSize = "Grande (25 à 45Kg)"

        every {
            this@PetViewModelRaceSizeTest.validation.validateDropdown(
                newSize,
                listPetSizesCat
            )
        } returns ValidationResult(
            success = true
        )

        viewModelTest.change(petSize = newSize)
        assertEquals(newSize, viewModelTest.state.size)
        viewModelTest.state = viewModelTest.state.copy(sizeError = null)
        assertEquals(null, viewModelTest.state.sizeError)
    }

    @Test
    fun `when change() is called with another race, it must change the race if it is in the list`() {
        val newRace = "Akita"

        every {
            this@PetViewModelRaceSizeTest.validation.validateDropdown(
                newRace,
                listPetSizesDog
            )
        } returns ValidationResult(
            success = true
        )

        viewModelTest.change(petRace = newRace)
        assertEquals(newRace, viewModelTest.state.race)
        viewModelTest.state = viewModelTest.state.copy(raceError = null)
        assertEquals(null, viewModelTest.state.raceError)
    }

    @Test
    fun `when change() is called with race others, it should change the race others`() {
        val newRaceOther = "Outro"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newRaceOther)
        } returns ValidationResult(
            success = true
        )

        viewModelTest.change(petRaceOthers = newRaceOther)
        assertEquals(newRaceOther, viewModelTest.state.raceOthers)
        assertEquals(null, viewModelTest.state.raceOthersError)
    }

    @Test
    fun `You should not accept any other race of pet with a special character`() {
        val newRaceOther = "Raça#@s"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newRaceOther)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petRaceOthers = newRaceOther)
        assertEquals(newRaceOther, viewModelTest.state.raceOthers)
        assertNotNull(viewModelTest.state.raceOthersError)
    }

    @Test
    fun `should not accept another race of empty pet`() {
        val newRaceOther = ""

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newRaceOther)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petRaceOthers = newRaceOther)
        assertEquals(newRaceOther, viewModelTest.state.raceOthers)
        assertNotNull(viewModelTest.state.raceOthersError)
    }

    @Test
    fun `should not accept other pet race with less than 2 characters`() {
        // Testa se o método change() não aceita nomes com menos de 2 caracteres
        val newRaceOther = "A"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newRaceOther)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petRaceOthers = newRaceOther)
        assertEquals(newRaceOther, viewModelTest.state.raceOthers)
        assertNotNull(viewModelTest.state.raceOthersError)
    }

    @Test
    fun `You should not accept other pet race with more than 30 characters`() {
        val newRaceOther = "Shoryukenhadoukentatsumakisenpukyaku"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newRaceOther)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")
        )
        viewModelTest.change(petRaceOthers = newRaceOther)
        assertEquals(newRaceOther, viewModelTest.state.raceOthers)
        assertNotNull(viewModelTest.state.raceOthersError)
    }

    @Test
    fun `should not accept any other race of pet with a number`() {
        val newRaceOther = "Sc0rp10n"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newRaceOther)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")
        )
        viewModelTest.change(petRaceOthers = newRaceOther)
        assertEquals(newRaceOther, viewModelTest.state.raceOthers)
        assertNotNull(viewModelTest.state.raceOthersError)
    }

    @Test
    fun `You should not accept a pet size that is not on the size list`() {
        val newSize = "Pequeno"

        every {
            this@PetViewModelRaceSizeTest.validation.validateDropdown(newSize, listPetSizesCat)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Error")
        )

        viewModelTest.change(petSize = newSize)
        assertEquals(newSize, viewModelTest.state.size)
        assertNotNull(viewModelTest.state.sizeError)
    }

    @Test
    fun `You should not accept a pet race that is not on the race list`() {
        val newRace = "Raça errada"

        every {
            this@PetViewModelRaceSizeTest.validation.validateDropDownPetRace(newRace, listDogRaces)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Error")
        )

        viewModelTest.change(petRace = newRace)
        assertEquals(newRace, viewModelTest.state.race)
        assertNotNull(viewModelTest.state.raceError)
    }

    @Test
    fun `OnEvent should allow size race changes`() {
        val newSize = "Grande (a cima de 25kg)"
        val event = RaceSizeFormEvent.PetSize(petSize = newSize)
        viewModelTest.onEvent(event)
        assertEquals(newSize, viewModelTest.state.size)
        assertEquals(emptyList<String>(), viewModelTest.state.sizeError)
    }

    @Test
    fun `OnEvent should allow pet race changes`() {
        val newRace = "Akita"
        val event = RaceSizeFormEvent.PetRace(petRace = newRace)
        viewModelTest.onEvent(event)
        assertEquals(newRace, viewModelTest.state.race)
        assertEquals(emptyList<String>(), viewModelTest.state.raceError)
    }

    @Test
    fun `OnEvent should allow other pet race changes`() {
        val newRaceOthers = "Other Race"
        val event = RaceSizeFormEvent.PetRaceOthers(petRaceOthers = newRaceOthers)
        viewModelTest.onEvent(event)
        assertEquals(newRaceOthers, viewModelTest.state.raceOthers)
        assertEquals(emptyList<String>(), viewModelTest.state.raceOthersError)
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
        assertEquals("Sucesso", viewModelTest.message.value)
    }

    @Test
    fun `should return error message if petInformation retrieval from room fails`() {

        coEvery { getPetInformationUseCase.execute(any()) } returns DataResult.Failure(
            Throwable()
        )
        viewModelTest.getPetInformation(2)
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
    @Test
    fun `should return pet size list for dog`(){
        coEvery { getListPetSizesUseCase.execute(any())
        }returns  DataResult.Success(
            listPetSizesDog
        )
        viewModelTest.state = viewModelTest.state.copy(specie = "Dog")
        viewModelTest.requestGetListSizes()
        assertEquals("Sucesso", viewModelTest.message.value)
    }
    @Test
    fun `should return pet size list for Cat`(){
        coEvery { getListPetSizesUseCase.execute(any())
        }returns  DataResult.Success(
            listPetSizesCat
        )
        viewModelTest.state = viewModelTest.state.copy(specie = "Cat")
        viewModelTest.requestGetListSizes()
        assertEquals("Sucesso", viewModelTest.message.value)
    }
    @Test
    fun `get should return error message if list is error`() {

        coEvery { getListPetSizesUseCase.execute(any()) } returns DataResult.Failure(
            Throwable()
        )
        viewModelTest.state = viewModelTest.state.copy(specie = "Dog")
        viewModelTest.requestGetListSizes()
        assertEquals("Error", viewModelTest.message.value)
    }

    @Test
    fun `should return list of pet race for dog`(){
        coEvery { getListPetRacesUseCase.execute(any())
        }returns  DataResult.Success(
            listDogRaces
        )
        viewModelTest.state = viewModelTest.state.copy(specie = "Dog")
        viewModelTest.requestGetListRaces()
        assertEquals("Sucesso", viewModelTest.message.value)
    }
    @Test
    fun `must return list of pet race for Cat`(){
        coEvery { getListPetRacesUseCase.execute(any())
        }returns  DataResult.Success(
            listCatsRace
        )
        viewModelTest.state = viewModelTest.state.copy(specie = "Cat")
        viewModelTest.requestGetListRaces()
        assertEquals("Sucesso", viewModelTest.message.value)
    }
    @Test
    fun `get should return an error message if the pet breed list returns an error`() {
        coEvery { getListPetRacesUseCase.execute(any()) } returns DataResult.Failure(
            Throwable()
        )
        viewModelTest.state = viewModelTest.state.copy(specie = "Cat")
        viewModelTest.requestGetListRaces()
        assertEquals("Error", viewModelTest.message.value)
    }

}