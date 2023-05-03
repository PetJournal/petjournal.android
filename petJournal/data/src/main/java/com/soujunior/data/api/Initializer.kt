/*
package com.soujunior.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Initializer {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("URL_BASE")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val getService: Service = retrofit.create(Service::class.java)
}*/
