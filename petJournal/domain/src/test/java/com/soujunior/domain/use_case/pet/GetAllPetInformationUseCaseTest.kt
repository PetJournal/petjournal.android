package com.soujunior.domain.use_case.pet

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.setup.petInformationItemList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class GetAllPetInformationUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<GuardianRepository>(relaxed = true)

    @Test
    fun `should return failure when get pet information`() = runBlocking {
        coEvery { repository.getAllPetInformation() } returns NetworkResult.Exception(
            Throwable()
        )
        val getAllPetInformationUseCase = GetAllPetInformationUseCase(repository = repository)

        val result = getAllPetInformationUseCase.execute(Unit)

        assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `should return success when get pet information`() = runBlocking {

        coEvery { repository.getAllPetInformation() } returns NetworkResult.Success(data = petInformationItemList)
        val getAllPetInformationUseCase = GetAllPetInformationUseCase(repository = repository)

        val result = getAllPetInformationUseCase.execute(Unit)

        assertThat(result.success.data).isEqualTo(petInformationItemList)
    }
}