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

class GetPetInformationUseCaseTest{
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<GuardianRepository>(relaxed = true)

    @Test
    fun `failure get pet information`() = runBlocking {
        coEvery { repository.getPetInformation(petInformation.id) } returns DataResult.Failure(
            Throwable()
        )
        val getPetInformationUseCase = GetPetInformationUseCase(repository = repository)

        val result = getPetInformationUseCase.execute(petInformation.id)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `successful in get pet Information`() = runBlocking {

        coEvery { repository.getPetInformation(petInformation.id) } returns DataResult.Success(data = petInformation)
        val getPetInformationUseCase = GetPetInformationUseCase(repository = repository)

        val result = getPetInformationUseCase.execute(petInformation.id)

        assertk.assertThat(result.success.data).isEqualTo(petInformation)
    }

}