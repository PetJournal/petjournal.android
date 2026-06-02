/*
package com.soujunior.data.api

import io.mockk.every
import io.mockk.mockk

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ServiceForgotPasswordTest {
    private val callMock = mockk<Call<ApiResponseCode>>()

    @Test
    fun `forgot password success scenario`() {
        val response = ApiResponseCode(200, "Success")

        every { callMock.execute() } returns Response.success(response)

        val result = ServiceMock(200).forgotPassword()

        assertTrue(result.execute().isSuccessful)
        assertEquals(200, result.execute().body()?.code)
    }

    @Test
    fun `forgot password error scenario`() {

        every { callMock.execute() } returns Response.error(
            400, ResponseBody.create(
                MediaType.parse("application/json"),
                "Error message"
            )
        )

        val result = ServiceMock(400).forgotPassword()

        assertFalse(result.execute().isSuccessful)
        assertEquals("Response.error()", result.execute().message())
        assertEquals(400, result.execute().code())
    }

    @Test
    fun `forgot password error when processing scenario`() {

        every { callMock.execute() } returns Response.error(
            500, ResponseBody.create(
                MediaType.parse("application/json"),
                "Error message"
            )
        )

        val result = ServiceMock( 500).forgotPassword()

        assertFalse(result.execute().isSuccessful)
        assertNull(result.execute().body())
        assertEquals(500, result.execute().code())
    }
}*/
