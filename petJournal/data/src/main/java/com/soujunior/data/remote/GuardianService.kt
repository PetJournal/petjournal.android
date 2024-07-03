package com.soujunior.data.remote

import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.PetInformationResponse
import com.soujunior.domain.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface GuardianService {
    @GET("api/guardian/name")
    suspend fun getGuardianName(@Header("Authorization") token: String): NetworkResult<GuardianNameResponse>

    @GET("api/sizes/{petSpecie}")
    suspend fun getListPetSizes(
        @Header("Authorization") token: String,
        @Path("petSpecie") petSpecie: String
    ): NetworkResult<List<PetSizeItemModel>>

    @GET("api/breeds/{petSpecie}")
    suspend fun getListPetRaces(
        @Header("Authorization") token: String,
        @Path("petSpecie") petSpecie: String
    ): NetworkResult<List<PetRaceItemModel>>

    @POST("api/pet")
    suspend fun savePetInformation(
        @Header("Authorization") token: String,
        @Body petInformationResponse: PetInformationResponse
    ): NetworkResult<Unit>
}