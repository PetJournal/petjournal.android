package com.soujunior.domain.usecase.auth

import assertk.assertions.isEqualTo
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.setup.formLogin
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class LoginUseCaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val repository = mockk<AuthRepository>(relaxed = true)

    @Test
    fun `failure Server Error in user login code 100`() = runBlocking {
        coEvery { repository.login(formLogin) } returns ApiResponseCode(
            100,
            "The server is still processing the request."
        )
        val loginUseCase = LoginUseCase(repository = repository)

        val result = loginUseCase.execute(formLogin)

        assertk.assertThat(result.success.data)
            .isEqualTo("The server is still processing the request.")
    }

    @Test
    fun `successful in user login code 200`() = runBlocking {
        coEvery { repository.login(formLogin) } returns ApiResponseCode(200, "Success")
        val loginUseCase = LoginUseCase(repository = repository)

        val result = loginUseCase.execute(formLogin)

        assertk.assertThat(result.success.data).isEqualTo("Success")
    }

    @Test
    fun `failure There is some data missing from the form in user login code 300`() = runBlocking {
        coEvery { repository.login(formLogin) } returns ApiResponseCode(
            300,
            "There is some data missing from the form"
        )
        val loginUseCase = LoginUseCase(repository = repository)

        val result = loginUseCase.execute(formLogin)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `failure Client Error in user login code 400`() = runBlocking {
        coEvery { repository.login(formLogin) } returns ApiResponseCode(400, "Bad Request")
        val loginUseCase = LoginUseCase(repository = repository)

        val result = loginUseCase.execute(formLogin)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }

    @Test
    fun `failure Error processing request Error in user login code 500`() = runBlocking {
        coEvery { repository.login(formLogin) } returns ApiResponseCode(
            500,
            "Error processing request"
        )
        val loginUseCase = LoginUseCase(repository = repository)

        val result = loginUseCase.execute(formLogin)

        assertk.assertThat(result.isFailure).isEqualTo(true)
    }
}
