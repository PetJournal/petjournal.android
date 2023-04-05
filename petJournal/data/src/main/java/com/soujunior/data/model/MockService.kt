package com.soujunior.data.model

import com.soujunior.data.api.Service
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.RegisterModel
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MockService : Service {
    override fun register(registerData: RegisterModel): Call<ApiResponseCode> {
        val responseCode = ApiResponseCode(200, "Sucess Mock") //Success
        return object : Call<ApiResponseCode> {
            override fun enqueue(callback: Callback<ApiResponseCode>) {
                callback.onResponse(this, Response.success(responseCode))
            }
            override fun isExecuted(): Boolean {
                return false
            }
            override fun clone(): Call<ApiResponseCode> {
                return this
            }
            override fun isCanceled(): Boolean {
                return false
            }
            override fun cancel() {}
            override fun execute(): Response<ApiResponseCode> {
                return Response.success(responseCode)
            }
            override fun request(): Request {
                return Request.Builder().url("https://example.com").build()
            }
            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }

        }
    }
}