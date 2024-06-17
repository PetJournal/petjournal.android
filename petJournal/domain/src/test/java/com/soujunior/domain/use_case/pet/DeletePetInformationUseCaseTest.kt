package com.soujunior.domain.use_case.pet

import assertk.assertThat
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

class DeletePetInformationUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<GuardianRepository>(relaxed = true)

    @Test
    fun `should return fail when delete pet information`() = runBlocking {
        coEvery { repository.deletePetInformation(petInformation.id) } returns DataResult.Failure(
            Throwable()
        )
        val deletePetInformationUseCase = DeletePetInformationUseCase(repository = repository)

        val result = deletePetInformationUseCase.execute(petInformation.id)

        assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `should return success when delete pet information`() = runBlocking {

        coEvery { repository.deletePetInformation(petInformation.id) } returns DataResult.Success(data = Unit)
        val deletePetInformationUseCase = DeletePetInformationUseCase(repository = repository)

        val result = deletePetInformationUseCase.execute(petInformation.id)

        assertk.assertThat(result.isSuccess).isEqualTo(true)
    }
}