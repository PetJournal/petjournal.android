package com.soujunior.data.remote

import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.PetCreateDTO
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.pet.PetInformationResponse
import com.soujunior.domain.model.response.pet.PetResponse
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.network.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @Multipart
    @POST("api/pet")
    suspend fun createPet(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("specieName") specieName: RequestBody,
        @Part("petName") petName: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("breedName") breedName: RequestBody,
        @Part("size") size: RequestBody,
        @Part("castrated") castrated: RequestBody,
        @Part("dateOfBirth") dateOfBirth: RequestBody
    ): NetworkResult<PetDetailsDTO>

    @GET("api/pet")
    suspend fun getPetList(
        @Header("Authorization") token: String
    ): NetworkResult<List<PetDetailsDTO>>

    @GET("api/pet/{id}")
    suspend fun getPetById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): NetworkResult<PetResponse>

    @DELETE("api/pet/{id}")
    suspend fun deletePetById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): NetworkResult<Unit>

    @PUT("api/pet/{id}")
    suspend fun updatePetById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body petInformationResponse: PetInformationResponse
    ): NetworkResult<Unit>

    /**
     * animal can be "cat" or "dog"
     * **/
    @GET("api/breeds/{animal}")
    suspend fun getListBreeds(
        @Header("Authorization") token: String,
        @Path("animal") animal: String,
    ): NetworkResult<List<BreedDTO>>

    /**
     * animal can be "cat" or "dog"
     * **/
    @GET("api/sizes/{animal}")
    suspend fun getListSize(
        @Header("Authorization") token: String,
        @Path("animal") animal: String,
    ): NetworkResult<List<SizeDTO>>

    @GET("api/tag")
    suspend fun getListTag(
        @Header("Authorization") token: String
    ): NetworkResult<List<TagDTO>>

    @POST("api/tag")
    suspend fun createTag(
        @Header("Authorization") token: String,
        @Body tagDTO: TagDTO
    ): NetworkResult<TagDTO>

    @PUT("api/tag/{id}")
    suspend fun updateTag(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body tagDTO: TagDTO
    ): NetworkResult<UpdatePetByIdDTO>

    @DELETE("api/tag/{id}")
    suspend fun deleteTag(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): NetworkResult<Unit>
}

