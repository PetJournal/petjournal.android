package com.soujunior.data.repository

import android.content.Context
import com.soujunior.data.api.Service
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Call

class AuthRepositoryImplForgotPasswordTest {
    private val serviceMock = mockk<Service>()
    private val form = ForgotPasswordModel("user@example.com")
    private val contextMock = mockk<Context>()
    private val repository = AuthRepositoryImpl(serviceMock, contextMock)

    @Test
    fun `success scenario`() = runBlocking {

        val expectedResponse = ApiResponseCode(200, "Codigo enviado para o email de cadastro!")
        val callMock = mockk<Call<ApiResponseCode>>()

        coEvery { serviceMock.forgotPassword(form) } returns callMock

        coEvery { callMock.enqueue(any()) } answers {
            val callback = arg<retrofit2.Callback<ApiResponseCode>>(0)
            callback.onResponse(callMock, retrofit2.Response.success(expectedResponse))
        }

        val result = repository.forgotPassword(form)

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `error scenario - No internet connection`() = runBlocking {
        coEvery { serviceMock.forgotPassword(form) } throws Exception("Erro na conexão com a internet!")
        try {
            repository.forgotPassword(form)
        } catch (_: java.lang.Exception) {
        }

        verify { serviceMock.forgotPassword(form) }
    }

    @Test
    fun `error scenario - API Error`() = runBlocking {

        val callMock = mockk<Call<ApiResponseCode>>()
        coEvery { serviceMock.forgotPassword(form) } returns callMock

        coEvery { callMock.enqueue(any()) } answers {
            val callback = arg<retrofit2.Callback<ApiResponseCode>>(0)
            callback.onResponse(
                callMock, retrofit2.Response.error(
                    500, ResponseBody.create(
                        MediaType.parse("application/json"),
                        "Error message"
                    )
                )
            )
        }

        val result = repository.forgotPassword(form)

        assertEquals(500, result.code)

    }
}