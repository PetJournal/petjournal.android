package com.soujunior.domain.use_case.auth

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.response.AccessTokenResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.use_case.base.DataResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AwaitingCodeUseCaseTest {

    @get:Rule
    var coroutineTestRule = MainCoroutineRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var awaitingCodeModel: AwaitingCodeModel



    @Before
    fun setup() {
        authRepository = mockk<AuthRepository>(relaxed = true)
        awaitingCodeModel = mockk<AwaitingCodeModel>(relaxed = true)
    }

    @Test
    fun `awaitingCode returns AccessTokenResponse with expected accessToken on success`() = runTest {
        coEvery {
            authRepository.awaitingCode(any())
        } returns NetworkResult.Success(AccessTokenResponse("4a43-gkd4-asmb-rasb"))

        val result = authRepository.awaitingCode(awaitingCodeModel)

        when (result) {
            is NetworkResult.Success -> {
                val accessToken = result.data.accessToken
                assertThat(accessToken).isEqualTo("4a43-gkd4-asmb-rasb")
            }
            else -> fail("Expected NetworkResult.Success but got $result")
        }
    }
    @Test
    fun `awaitingCode returns AccessTokenResponse with no expected accessToken on success`() = runTest {
        coEvery {
            authRepository.awaitingCode(any())
        } returns NetworkResult.Success(AccessTokenResponse("4a43-gkd4-asmb-rasb"))

        val result = authRepository.awaitingCode(awaitingCodeModel)

        when (result) {
            is NetworkResult.Success -> {
                val accessToken = result.data.accessToken
                assertThat(accessToken).isNotEqualTo("4a43-gkd4-asmb-ras")
            }
            else -> fail("Expected NetworkResult.Success but got $result")
        }
    }
}