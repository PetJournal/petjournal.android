package com.soujunior.petjournal.ui.petRaceAndSize

import androidx.lifecycle.viewModelScope
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.RaceSizeFormEvent
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.RaceSizeFormState
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.ViewModelRaceSizeImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
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

    @Before
    fun setup() {
        // Configuração inicial para os testes
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModelTest = ViewModelRaceSizeImpl(validation)
    }

    @After
    fun tearDown() {
        // Limpa recursos após os testes
        viewModelTest.viewModelScope.cancel()
    }

    @Test
    fun `habilitar botão quando todos os dados forem validados`() {
        // Testa se o botão é habilitado quando todos os dados são válidos
        every { this@PetViewModelRaceSizeTest.validation.inputPetName(any()) } returns ValidationResult(
            success = true
        )

        every { this@PetViewModelRaceSizeTest.validation.inputPetGender(any()) } returns ValidationResult(
            success = true
        )

        viewModelTest.state = RaceSizeFormState(
            race = "Bolinha",
            size = "M",
            raceOthers = ""
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isTrue()
    }

    @Test
    fun `não é possível habilitar o botão com nome vazio`() {
        // Testa se o botão não é habilitado com um nome vazio
        viewModelTest.state = RaceSizeFormState(
            race = "",
            size = "M"
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `não é possível habilitar o botão com gênero vazio`() {
        // Testa se o botão não é habilitado com um gênero vazio
        viewModelTest.state = RaceSizeFormState(
            size = "Bolinha",
            race = ""
        )
        val enableButton = viewModelTest.enableButton()
        assertThat(enableButton).isFalse()
    }

    @Test
    fun `quando change() é chamado com outro nome, deve alterar o nome`() {
        // Testa se o método change() altera o nome corretamente
        val newName = "Stone Cold Steve Austin"

        every { this@PetViewModelRaceSizeTest.validation.inputPetName(newName) } returns ValidationResult(
            success = true
        )

        viewModelTest.change(petSize = newName)
        assertEquals(newName, viewModelTest.state.size)
        assertEquals(null, viewModelTest.state.sizeError)
    }

    @Test
    fun `quando change() é chamado com outro gênero, deve alterar o gênero`() {
        // Testa se o método change() altera o gênero corretamente
        val newGender = "F"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetGender(newGender)
        } returns ValidationResult(
            success = true,
        )

        viewModelTest.change(petRace = newGender)
        assertEquals(newGender, viewModelTest.state.race)
        assertEquals(null, viewModelTest.state.raceError)
    }

    @Test
    fun `não deve aceitar nome de pet com caractere especial`() {
        // Testa se o método change() não aceita nomes com caracteres especiais
        val newName = "Bolin#@s"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newName)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petSize = newName)
        assertEquals(newName, viewModelTest.state.size)
        assertNotNull(viewModelTest.state.sizeError)
    }

    @Test
    fun `não deve aceitar nome de pet vazio`() {
        // Testa se o método change() não aceita nomes vazios
        val newName = ""

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newName)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petSize = newName)
        assertEquals(newName, viewModelTest.state.size)
        assertNotNull(viewModelTest.state.sizeError)
    }

    @Test
    fun `não deve aceitar nome de pet com menos de 2 caracteres`() {
        // Testa se o método change() não aceita nomes com menos de 2 caracteres
        val newName = "A"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newName)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petSize = newName)
        assertEquals(newName, viewModelTest.state.size)
        assertNotNull(viewModelTest.state.sizeError)
    }

    @Test
    fun `não deve aceitar nome de pet com mais de 30 caracteres`() {
        // Testa se o método change() não aceita nomes com mais de 30 caracteres
        val newName = "Shoryukenhadoukentatsumakisenpukyaku"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newName)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")

        )
        viewModelTest.change(petSize = newName)
        assertEquals(newName, viewModelTest.state.size)
        assertNotNull(viewModelTest.state.sizeError)
    }

    @Test
    fun `deve aceitar nome de pet com número`() {
        // Testa se o método change() aceita nomes com números
        val newName = "Sc0rp10n"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetName(newName)
        } returns ValidationResult(
            success = true
        )
        viewModelTest.change(petSize = newName)
        assertEquals(newName, viewModelTest.state.size)
        assertEquals(null, viewModelTest.state.sizeError)
    }

    @Test
    fun `não deve aceitar outros gêneros além de masculino ou feminino`() {
        // Testa se o método change() não aceita outros gêneros além de "M" ou "F"
        val newGender = "X"

        every {
            this@PetViewModelRaceSizeTest.validation.inputPetGender(newGender)
        } returns ValidationResult(
            success = false,
            errorMessage = listOf("Erro")
        )

        viewModelTest.change(petRace = newGender)
        assertEquals(newGender, viewModelTest.state.race)
        assertNotNull(viewModelTest.state.raceError)
    }

    @Test
    fun `OnEvent deve permitir alterações de nome de pet`() {
        // Testa se o método OnEvent permite alterações de nome de pet
        val newName = "Blastoise"
        val event = RaceSizeFormEvent.PetSize(petSize = newName)
        viewModelTest.onEvent(event)
        assertEquals(newName, viewModelTest.state.size)
        assertEquals(emptyList<String>(), viewModelTest.state.sizeError)
    }

    @Test
    fun `OnEvent deve permitir alterações de gênero de pet`() {
        // Testa se o método OnEvent permite alterações de gênero de pet
        val newGender = "F"
        val event = RaceSizeFormEvent.PetRace(petRace = newGender)
        viewModelTest.onEvent(event)
        assertEquals(newGender, viewModelTest.state.race)
        assertEquals(emptyList<String>(), viewModelTest.state.raceError)
    }

}