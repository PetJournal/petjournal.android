package com.soujunior.domain.use_case.pet

import assertk.assertions.isEqualTo
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.setup.petInformation
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class CreatePetInformationApiUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<GuardianRepository>(relaxed = true)

    @Test
    fun `failure create pet information`() = runBlocking {
        coEvery { repository.createPetInformationApi(petInformation) } returns NetworkResult.Exception(
            Throwable()
        )
        val createPetInformation = CreatePetInformationApiUseCase(repository = repository)

        val result = createPetInformation.execute(petInformation)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `successful in create pet Information`() = runBlocking {
        coEvery { repository.createPetInformationApi(petInformation) } returns NetworkResult.Success(data = Unit)
        val createPetInformationUseCase = CreatePetInformationApiUseCase(repository = repository)

        val result = createPetInformationUseCase.execute(petInformation)

        assertk.assertThat(result.success.data).isEqualTo(Unit)
    }

}