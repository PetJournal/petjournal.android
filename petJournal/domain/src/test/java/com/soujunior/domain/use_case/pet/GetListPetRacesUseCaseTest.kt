package com.soujunior.domain.use_case.pet

import assertk.assertions.isEqualTo
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.setup.listCatsRace
import com.soujunior.domain.setup.petInformation
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class GetListPetRacesUseCaseTest{
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<GuardianRepository>(relaxed = true)

    @Test
    fun `failure get list pet races`() = runBlocking {
        coEvery { repository.getListPetRaces(petInformation.species ?: "Cat") } returns NetworkResult.Exception(
            Throwable()
        )
        val getListPetRacesUseCase = GetListPetRacesUseCase(repository = repository)

        val result = getListPetRacesUseCase.execute(petInformation.species ?: "Cat")

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `successful in get list pet races`() = runBlocking {

        coEvery { repository.getListPetRaces(petInformation.species ?: "Cat") } returns NetworkResult.Success(data = listCatsRace)
        val getListPetRacesUseCase = GetListPetRacesUseCase(repository = repository)

        val result = getListPetRacesUseCase.execute(petInformation.species ?: "Cat")

        assertk.assertThat(result.success.data).isEqualTo(listCatsRace)
    }

}