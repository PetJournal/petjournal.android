package com.soujunior.domain.usecase.auth

import assertk.assertions.isEqualTo
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.setup.formForgot
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

import org.junit.Rule

import org.junit.Test

class ForgotPasswordUseCaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<AuthRepository>(relaxed = true)

    @Test
    fun `failure Server Error in the user registry code 100`() = runBlocking {
        coEvery { repository.forgotPassword(formForgot) } returns ApiResponseCode(
            100,
            "The server is still processing the request."
        )
        val forgotPasswordUseCase = ForgotPasswordUseCase(repository = repository)

        val result = forgotPasswordUseCase.execute(formForgot)

        assertk.assertThat(result.success.data).isEqualTo("The server is still processing the request.")
    }

    @Test
    fun `successful in the user registry code 200`() = runBlocking {
        coEvery { repository.forgotPassword(formForgot) } returns ApiResponseCode(200, "Success")
        val forgotPasswordUseCase = ForgotPasswordUseCase(repository = repository)

        val result = forgotPasswordUseCase.execute(formForgot)

        assertk.assertThat(result.success.data).isEqualTo("Success")
    }

    @Test
    fun `failure There is some data missing from the form in the user registry code 300`() = runBlocking {
        coEvery { repository.forgotPassword(formForgot) } returns ApiResponseCode(
            300,
            "There is some data missing from the form"
        )
        val forgotPasswordUseCase = ForgotPasswordUseCase(repository = repository)

        val result = forgotPasswordUseCase.execute(formForgot)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `failure Client Error in the user registry code 400`() = runBlocking {
        coEvery { repository.forgotPassword(formForgot) } returns ApiResponseCode(400, "Bad Request")
        val forgotPasswordUseCase = ForgotPasswordUseCase(repository = repository)

        val result = forgotPasswordUseCase.execute(formForgot)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `failure Error processing request Error in the user registry code 500`() = runBlocking {
        coEvery { repository.forgotPassword(formForgot) } returns ApiResponseCode(
            500,
            "Error processing request"
        )
        val forgotPasswordUseCase = ForgotPasswordUseCase(repository = repository)

        val result = forgotPasswordUseCase.execute(formForgot)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }
}