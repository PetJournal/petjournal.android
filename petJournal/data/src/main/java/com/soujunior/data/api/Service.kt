package com.soujunior.data.api

import retrofit2.Call
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.RegisterModel
import retrofit2.http.*

interface Service {
    @POST("products/categories")
    fun register(@Body registerData: RegisterModel): Call<ApiResponseCode>
}