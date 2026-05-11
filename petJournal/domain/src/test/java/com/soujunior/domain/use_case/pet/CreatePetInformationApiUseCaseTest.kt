package com.soujunior.domain.use_case.pet

import assertk.assertions.isEqualTo
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
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

    private val repository = mockk<Repository>(relaxed = true)

    @Test
    fun `failure create pet information`() = runBlocking {
        coEvery { repository.createPet(petInformation) } returns NetworkResult.Exception(
            Throwable()
        )
        val createPetInformation = CreatePetUseCase(repository = repository)

        val result = createPetInformation.execute(petInformation)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `successful in create pet Information`() = runBlocking {
        coEvery { repository.createPet(petInformation) } returns NetworkResult.Success(data = Unit)
        val createPetInformationUseCase = CreatePetUseCase(repository = repository)

        val result = createPetInformationUseCase.execute(petInformation)

        assertk.assertThat(result.success.data).isEqualTo(Unit)
    }

}
