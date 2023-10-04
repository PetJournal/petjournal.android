/*
package com.soujunior.data.api

import com.soujunior.domain.entities.auth.ApiResponseCode
import io.mockk.every
import io.mockk.mockk
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class ServiceMock(private val code: Int) {
    fun forgotPassword(): Call<ApiResponseCode> {
        when (code) {
            200 -> {
                return mockk {
                    val response = ApiResponseCode(200, "Success")
                    every { execute() } returns Response.success(response)
                }
            }

            400 -> {
                return mockk {
                    every { execute() } returns Response.error(
                        400, ResponseBody.create(
                            MediaType.parse("application/json"),
                            "Response Error"
                        )
                    )
                }

            }

            else -> {
                return mockk {
                    every { execute() } returns Response.error(
                        500, ResponseBody.create(
                            MediaType.parse("application/json"),
                            "Response Error"
                        )
                    )
                }

            }
        }
    }
}*/
