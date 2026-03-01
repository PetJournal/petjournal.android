package com.soujunior.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import com.soujunior.data.remote.GuardianService
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.PetCreateDTO
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.network.onError
import com.soujunior.domain.network.onException
import com.soujunior.domain.network.onSuccess
import com.soujunior.domain.repository.GuardianLocalDataSource
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.DataResult
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

class GuardianRepositoryImpl(
    private val guardianApi: GuardianService,
    private val guardianLocalDataSourceImpl: GuardianLocalDataSource,
    private val context: Context
) : GuardianRepository {

    private val jwtManager: JwtManager = JwtManager.getInstance(context)

    internal fun getToken(): String? {
        return try {
            jwtManager.getToken()
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl-getToken", e.message.toString())
            null
        }
    }

    override suspend fun getGuardianName(): NetworkResult<GuardianNameResponse> {
        val localName = guardianLocalDataSourceImpl.getGuardianName()
        return if (localName != null) {
            NetworkResult.Success(GuardianNameResponse(localName, ""))
        } else {
            val token = "Bearer " + jwtManager.getToken()
            when (val apiResult = guardianApi.getGuardianName(token)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveGuardianName(apiResult.data)
                        } catch (e: Exception) {
                            Log.e(TAG, "Exeption: " + e.message)
                        }
                    }
                    NetworkResult.Success(GuardianNameResponse(apiResult.data.firstName, ""))
                }

                else -> apiResult
            }
        }
    }

    override suspend fun savePet(petModel: PetModel): DataResult<Long> {
        val guardianId = 1
        val petInformation = petModel.copy(
            species = petModel.species,
            guardianId = guardianId
        )
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.savePetInformation(petInformation).success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun getListSize(animal: String): NetworkResult<List<SizeDTO>> {
        getToken()?.let { token ->
            val apiResponse =  guardianApi.getListSize(token, animal)
            var result: NetworkResult<List<SizeDTO>> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    result = NetworkResult.Success(data)
                }
                .onError { code, body ->
                    result = NetworkResult.Error(code, body)
                }
                .onException { throwable ->
                    result = NetworkResult.Exception(throwable)
                }
            return result
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun getListBreed(animal: String): NetworkResult<List<BreedDTO>> {
        getToken()?.let { token ->
            val apiResponse =  guardianApi.getListBreeds(token, animal)
            var result: NetworkResult<List<BreedDTO>> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    result = NetworkResult.Success(data)
                }
                .onError { code, body ->
                    result = NetworkResult.Error(code, body)
                }
                .onException { throwable ->
                    result = NetworkResult.Exception(throwable)
                }
            return result
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun getListTag(): NetworkResult<List<TagDTO>> {
        getToken()?.let { token ->
            val apiResponse =  guardianApi.getListTag(token)
            var result: NetworkResult<List<TagDTO>> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    result = NetworkResult.Success(data)
                }
                .onError { code, body ->
                    result = NetworkResult.Error(code, body)
                }
                .onException { throwable ->
                    result = NetworkResult.Exception(throwable)
                }
            return result
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun createTag(tag: TagDTO): NetworkResult<TagDTO> {
        getToken()?.let { token ->
            val apiResponse =  guardianApi.createTag(token, tag)
            var result: NetworkResult<TagDTO> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    result = NetworkResult.Success(data)
                }
                .onError { code, body ->
                    result = NetworkResult.Error(code, body)
                }
                .onException { throwable ->
                    result = NetworkResult.Exception(throwable)
                }
            return result
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun updateTag(tag: TagDTO): NetworkResult<UpdatePetByIdDTO> {
        getToken()?.let { token ->
            val apiResponse =  guardianApi.updateTag(token, tag.id!!, tag)
            var result: NetworkResult<UpdatePetByIdDTO> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    result = NetworkResult.Success(data)
                }
                .onError { code, body ->
                    result = NetworkResult.Error(code, body)
                }
                .onException { throwable ->
                    result = NetworkResult.Exception(throwable)
                }
            return result
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun deleteTag(id: String): NetworkResult<Unit> {
        getToken()?.let { token ->
            val apiResponse =  guardianApi.deleteTag(token, id)
            var result: NetworkResult<Unit> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    result = NetworkResult.Success(data)
                }
                .onError { code, body ->
                    result = NetworkResult.Error(code, body)
                }
                .onException { throwable ->
                    result = NetworkResult.Exception(throwable)
                }
            return result
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun getListPet(): NetworkResult<List<PetDetailsDTO>> {
        getToken()?.let { token ->
            val apiResponse =  guardianApi.getPetList(token)
            var result: NetworkResult<List<PetDetailsDTO>> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    result = NetworkResult.Success(data)
                }
                .onError { code, body ->
                    result = NetworkResult.Error(code, body)
                }
                .onException { throwable ->
                    result = NetworkResult.Exception(throwable)
                }
            return result
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun getPet(idPet: Long): DataResult<PetModel> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.getPetInformation(idPet).success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun updatePet(petModel: PetModel): DataResult<Unit> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.updatePetInformation(petModel).success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>> {
        val localListPetSizes =
            guardianLocalDataSourceImpl.getListPetSizes(petSpecie)?.success?.data
        return if (!localListPetSizes.isNullOrEmpty()) {
            NetworkResult.Success(localListPetSizes)
        } else {
            val token = "Bearer " + jwtManager.getToken()
            when (val apiResult = guardianApi.getListPetSizes(token, petSpecie)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveListPetSizes(petSpecie, apiResult.data)
                        } catch (e: Exception) {
                            Log.e(TAG, "Exeption: " + e.message)
                        }
                    }

                    NetworkResult.Success(apiResult.data)
                }

                else -> apiResult
            }
        }
    }

    override suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>> {
        val localListPetRaces =
            guardianLocalDataSourceImpl.getListPetRaces(petSpecie)?.success?.data
        return if (!localListPetRaces.isNullOrEmpty()) {
            NetworkResult.Success(localListPetRaces)
        } else {
            val token = "Bearer " + jwtManager.getToken()
            when (val apiResult = guardianApi.getListPetRaces(token, petSpecie)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveListPetRaces(petSpecie, apiResult.data)
                        } catch (e: Exception) {
                            Log.e(TAG, "Exeption: " + e.message)
                        }
                    }

                    NetworkResult.Success(apiResult.data)
                }

                else -> apiResult
            }
        }
    }

    private fun String?.toTextRequestBody(): RequestBody {
        val mediaType = MediaType.parse("text/plain")
        val content = this ?: ""
        return RequestBody.create(mediaType, content)
    }

    private fun Boolean?.toTextRequestBody(): RequestBody {
        val mediaType = MediaType.parse("text/plain")
        val content = (this ?: false).toString()
        return RequestBody.create(mediaType, content)
    }

    override suspend fun createPet(pet: PetCreateDTO, imageUri: String?): NetworkResult<PetDetailsDTO> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return try {
            val imageFile = imageUri?.let { getFileFromUri(context = context, Uri.parse(it)) }

            val imagePart: MultipartBody.Part = if (imageFile != null && imageFile.exists()) {
                val mediaType = MediaType.parse("image/*")
                val requestFile = RequestBody.create(mediaType, imageFile)
                MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
            } else {
                throw IllegalArgumentException("Imagem é obrigatória")
            }

            val specieNamePart = pet.specieName.toTextRequestBody()
            val petNamePart = pet.petName.toTextRequestBody()
            val genderPart = pet.gender.toTextRequestBody()
            val breedNamePart = pet.breedName.toTextRequestBody()
            val sizePart = pet.size.toTextRequestBody()
            val castratedPart = pet.castrated.toString().toTextRequestBody()
            val dateOfBirthPart = pet.dateOfBirth.toTextRequestBody()

            val apiResponse = guardianApi.createPet(
                token = token,
                image = imagePart,
                specieName = specieNamePart,
                petName = petNamePart,
                gender = genderPart,
                breedName = breedNamePart,
                size = sizePart,
                castrated = castratedPart,
                dateOfBirth = dateOfBirthPart
            )

            var result: NetworkResult<PetDetailsDTO> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    result = NetworkResult.Success(data)
                }
                .onError { code, body ->
                    result = NetworkResult.Error(code, body)
                }
                .onException { throwable ->
                    result = NetworkResult.Exception(throwable)
                }

            result
        } catch (e: Exception) {
            NetworkResult.Exception(e)
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File(context.cacheDir, "temp_pet_image_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(tempFile)

            inputStream.copyTo(outputStream)

            inputStream.close()
            outputStream.close()

            tempFile
        } catch (e: Exception) {
            Log.e("GuardianRepository", "Falha ao converter URI para File", e)
            null
        }
    }
}
