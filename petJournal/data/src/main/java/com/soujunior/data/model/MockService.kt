package com.soujunior.data.model

import android.util.Log
import com.soujunior.data.api.Service
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.RegisterModel
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MockService : Service {
    override fun register(registerData: RegisterModel): Call<ApiResponseCode> {
        val responseCode = ApiResponseCode(200, "Sucess Mock") //Success

        return object : Call<ApiResponseCode> {
            override fun enqueue(callback: Callback<ApiResponseCode>) {
                //
                when (responseCode.code) {
                    in 100..299 -> {
                        callback.onResponse(this, Response.success(responseCode.code, responseCode))
                    }
                    in 300..499 -> {
                        val errorResponseBody = ResponseBody.create(MediaType.parse("application/json"), "Error message")
                        val errorResponse = Response.error<ApiResponseCode>(responseCode.code, errorResponseBody)
                        callback.onResponse(this, errorResponse)
                    }
                    //in 500..599 -> "Erro ao processar requisição"
                    else -> callback.onResponse(this, Response.success(responseCode.code, responseCode))
                }

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
                return Response.success(responseCode.code, responseCode)
            }

            override fun request(): Request {
                return Request.Builder().url("https://example.com").build()
            }
            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }

        }

    }

    override fun login(loginData: LoginModel): Call<ApiResponseCode> {
        val responseCode = ApiResponseCode(200, "Success Mock")
        return object : Call<ApiResponseCode> {
            override fun clone(): Call<ApiResponseCode> {
                return this
            }

            override fun execute(): Response<ApiResponseCode> {
                return Response.success(responseCode)
            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun cancel() {

            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun request(): Request {
                return Request.Builder().url("https://example.com").build()
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }

            override fun enqueue(callback: Callback<ApiResponseCode>) {
                callback.onResponse(this, Response.success(responseCode))
            }

        }
    }
}