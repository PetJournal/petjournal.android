package com.soujunior.data.remote

import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.PetInformationDeleted
import com.soujunior.domain.model.response.PetInformationRequest
import com.soujunior.domain.model.response.pet_information.PetInformationItem
import com.soujunior.domain.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.DELETE
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
        @Body petInformationRequest: PetInformationRequest
    ): NetworkResult<Unit>

    @GET("api/pet")
    suspend fun getAllPetInformation(
        @Header("Authorization") token: String
    ): NetworkResult<List<PetInformationItem>>

    @DELETE("api/pet/{petId}")
    suspend fun deletePetInformation(
        @Header("Authorization") token: String,
        @Path("petId") petId: String
    ): NetworkResult<PetInformationDeleted>
}