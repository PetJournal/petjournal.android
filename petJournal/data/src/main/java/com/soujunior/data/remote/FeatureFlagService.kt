package com.soujunior.data.remote

import retrofit2.http.GET
import retrofit2.http.Url

interface FeatureFlagService {
    @GET
    suspend fun getFeatureFlags(@Url url: String): Map<String, Boolean>
}
