package com.soujunior.petjournal.ui.registeredPetScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.pet_information.Breed
import com.soujunior.domain.model.response.pet_information.PetInformationItem
import com.soujunior.domain.model.response.pet_information.Size
import com.soujunior.domain.model.response.pet_information.Specie
import com.soujunior.domain.use_case.base.DataResult
import com.soujunior.domain.use_case.pet.DeletePetInformationUseCase
import com.soujunior.domain.use_case.pet.GetAllPetInformationUseCase
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen.ViewModelRegisteredPetsImpl
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelRegisteredPetTest{
    private lateinit var viewModelTest: ViewModelRegisteredPetsImpl
    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)
    private val getAllPetInformationUseCase = mockk<GetAllPetInformationUseCase>(relaxed = true)
    private val deletePetInformationUseCase = mockk<DeletePetInformationUseCase>(relaxed = true)

    private val testDispatcher = TestCoroutineDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModelTest =
            ViewModelRegisteredPetsImpl(validation, getAllPetInformationUseCase, deletePetInformationUseCase)
    }

    @After
    fun tearDown() {
        viewModelTest.viewModelScope.cancel()
    }

    @Test
    fun `getAllPetInformation updates state and handles success`() = runTest {
        val petList = listOf(
            PetInformationItem(
                id = "1",
                guardianId = "123",
                specie = Specie("1", "Cachorro"),
                specieAlias = "Dog",
                petName = "Rex",
                gender = "Masculino",
                breedAlias = "Pastor Alemão",
                breed = Breed("1", "Pastor Alemão"),
                size = Size("1", "Grande"),
                castrated = true,
                dateOfBirth = "2018/5/20"
            ),
            PetInformationItem(
                id = "2",
                guardianId = "124",
                specie = Specie("2", "Gato"),
                specieAlias = "Cat",
                petName = "Mimi",
                gender = "Feminino",
                breedAlias = "Siamês",
                breed = Breed("2", "Siamês"),
                size = Size("2", "Pequeno"),
                castrated = false,
                dateOfBirth = "2020/8/15"
            )

        )
        val result = DataResult.Success(petList)

        coEvery { getAllPetInformationUseCase.execute(Unit) } returns result

        viewModelTest.getAllPetInformation()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(TaskState.Idle, viewModelTest.taskState.value)
    }

    @Test
    fun `getAllPetInformation updates state and handles failure`() = runTest {
        val error = Exception("Error fetching pet information")
        val result = DataResult.Failure(error)

        coEvery { getAllPetInformationUseCase.execute(Unit) } returns result

        viewModelTest.getAllPetInformation()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(TaskState.Idle, viewModelTest.taskState.value)
    }

    @Test
    fun `should set TaskState to Loading when deletePetInformation is called`() = runTest {
        coEvery { deletePetInformationUseCase.execute(1L) } returns DataResult.Success(Unit)

        viewModelTest.deletePetInformation(1L)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(TaskState.Idle, viewModelTest.taskState.value)
    }

    @Test
    fun `should call execute on deletePetInformationUseCase with correct petId`() = runTest {
        coEvery { deletePetInformationUseCase.execute(1L) } returns DataResult.Success(Unit)

        viewModelTest.deletePetInformation(1L)

        coVerify { deletePetInformationUseCase.execute(1L) }
    }

    @Test
    fun `should remove pet from registeredPetList when deletion is successful`() = runTest {
        val petToDeleteId = 1L
        viewModelTest.state = viewModelTest.state.copy(
            registeredPetList = listOf(
                PetInformationItem(
                    id = "1",
                    guardianId = "123",
                    specie = Specie("1", "Cachorro"),
                    specieAlias = "Dog",
                    petName = "Rex",
                    gender = "Masculino",
                    breedAlias = "Pastor Alemão",
                    breed = Breed("1", "Pastor Alemão"),
                    size = Size("1", "Grande"),
                    castrated = true,
                    dateOfBirth = "2018/5/20"
                ),
                PetInformationItem(
                    id = "2",
                    guardianId = "124",
                    specie = Specie("2", "Gato"),
                    specieAlias = "Cat",
                    petName = "Mimi",
                    gender = "Feminino",
                    breedAlias = "Siamês",
                    breed = Breed("2", "Siamês"),
                    size = Size("2", "Pequeno"),
                    castrated = false,
                    dateOfBirth = "2020/8/15"
                )
            )
        )
        coEvery { deletePetInformationUseCase.execute(1L) } returns DataResult.Success(Unit)

        viewModelTest.deletePetInformation(petToDeleteId)

        assertEquals(1, viewModelTest.state.registeredPetList.size)
        assertEquals("2", viewModelTest.state.registeredPetList[0].id)
    }

}