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

class UpdatePetInformationUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<GuardianRepository>(relaxed = true)

    @Test
    fun `failure update pet information`() = runBlocking {
        coEvery { repository.updatePetInformation(petInformation) } returns DataResult.Failure(
            Throwable()
        )
        val updatePetInformationUseCase = UpdatePetInformationUseCase(repository = repository)

        val result = updatePetInformationUseCase.execute(petInformation)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `successful in update pet Information`() = runBlocking {

        coEvery { repository.updatePetInformation(petInformation) } returns DataResult.Success(data = Unit)
        val updatePetInformationUseCase = UpdatePetInformationUseCase(repository = repository)

        val result = updatePetInformationUseCase.execute(petInformation)

        assertk.assertThat(result.isSuccess).isEqualTo(true)
    }
}