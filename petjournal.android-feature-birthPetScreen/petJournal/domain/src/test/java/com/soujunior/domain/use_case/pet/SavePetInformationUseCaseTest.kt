package com.soujunior.domain.use_case.pet

import assertk.assertions.isEqualTo
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.setup.petInformation
import com.soujunior.domain.use_case.base.DataResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class SavePetInformationUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<GuardianRepository>(relaxed = true)

    @Test
    fun `failure save pet information`() = runBlocking {
        coEvery { repository.savePetInformation(petInformation) } returns DataResult.Failure(
            Throwable()
        )
        val savePetInformation = SavePetInformationUseCase(repository = repository)

        val result = savePetInformation.execute(petInformation)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `successful in save pet Information`() = runBlocking {
        coEvery { repository.savePetInformation(petInformation) } returns DataResult.Success(1)
        val savePetInformationUseCase = SavePetInformationUseCase(repository = repository)

        val result = savePetInformationUseCase.execute(petInformation)

        assertk.assertThat(result.success.data).isEqualTo(1)
    }

}