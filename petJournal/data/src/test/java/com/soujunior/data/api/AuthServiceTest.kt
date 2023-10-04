package com.soujunior.data.api

import com.soujunior.data.remote.AuthService
import com.soujunior.domain.model.request.SignUpModel
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.network.ErrorBody
import com.soujunior.domain.network.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthServiceTest {

    private val authService: AuthService = mockk()

    /*@Test
    fun `signUp SUCCESS test`() = runBlockingTest {
        val signUpModel = SignUpModel(
            firstName = "fulano",
            lastName = "pimentel",
            email = "fulano@email.com",
            password = "AA@@11aa",
            passwordConfirmation = "AA@@11aa",
            phone = "91985158445",
            isPrivacyPolicyAccepted = true
        )
        val expectedResponse = UserInfoResponse(*//*...*//*) // Preencha com dados esperados

        coEvery { authService.signUp(signUpModel) } returns NetworkResult.Success(expectedResponse)

        val result = authService.signUp(signUpModel)

        Assert.assertTrue(result is NetworkResult.Success)
        Assert.assertEquals(expectedResponse, (result as NetworkResult.Success).data)
    }*/

    @Test
    fun `signUp ERROR test`() = runBlocking {
        val signUpModel = SignUpModel(
            firstName = "fulano",
            lastName = "pimentel",
            email = "fulano@email.com",
            password = "AA@@11aa",
            passwordConfirmation = "AA@@11aa",
            phone = "91985158445",
            isPrivacyPolicyAccepted = true
        )
        val expectedErrorCode = 400
        val expectedErrorMessage = "User already exists."

        coEvery {
            authService.signUp(signUpModel)
        } returns NetworkResult.Error(expectedErrorCode, ErrorBody(expectedErrorMessage))

        val result = authService.signUp(signUpModel)

        Assert.assertTrue(result is NetworkResult.Error)
        Assert.assertEquals(expectedErrorCode, (result as NetworkResult.Error).code)
        Assert.assertEquals(expectedErrorMessage, result.body?.error)
    }

}
