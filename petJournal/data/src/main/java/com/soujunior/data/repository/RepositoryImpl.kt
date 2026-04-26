package com.soujunior.data.repository

import android.content.Context
import android.net.Uri
import com.soujunior.data.remote.RemoteDataSource
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.PetCreateDTO
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.request.taskModels.TaskDTO
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.network.onError
import com.soujunior.domain.network.onException
import com.soujunior.domain.network.onSuccess
import com.soujunior.domain.repository.database.LocalDataSource
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.DataResult
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val guardianLocalDataSourceImpl: LocalDataSource,
    private val context: Context
) : Repository {

    private val jwtManager: JwtManager = JwtManager.getInstance(context)

    internal fun getToken(): String? {
        return try {
            val token = jwtManager.getToken()
            if (token != null && !token.startsWith("Bearer ", ignoreCase = true)) {
                "Bearer $token"
            } else {
                token
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getGuardianName(forceRequest: Boolean): NetworkResult<GuardianNameResponse> {
        if (!forceRequest) {
            val localName = guardianLocalDataSourceImpl.getGuardianName()
            if (localName != null) {
                return NetworkResult.Success(GuardianNameResponse(localName, ""))
            }
        }
        val token = getToken()
        return when (val apiResult = remoteDataSource.getGuardianName(token!!)) {
            is NetworkResult.Success -> {
                coroutineScope {
                    try {
                        guardianLocalDataSourceImpl.saveGuardianName(apiResult.data)
                    } catch (e: Exception) {
                    }
                }
                NetworkResult.Success(GuardianNameResponse(apiResult.data.firstName, ""))
            }

            else -> apiResult
        }
    }

    override suspend fun getGuardianEmail(): String? {
        return guardianLocalDataSourceImpl.getGuardianEmail()
    }

    override suspend fun saveGuardianContact(email: String, phone: String) {
        guardianLocalDataSourceImpl.saveGuardianContact(email, phone)
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
            val apiResponse =  remoteDataSource.getListSize(token, animal)
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
            val apiResponse =  remoteDataSource.getListBreeds(token, animal)
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

    override suspend fun getListTag(forceRequest: Boolean): NetworkResult<List<TagDTO>> {
        if (!forceRequest) {
            val localTags = guardianLocalDataSourceImpl.getAllTags()
            if (localTags.isNotEmpty()) {
                return NetworkResult.Success(localTags)
            }
        }
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.getListTag(token)
            var result: NetworkResult<List<TagDTO>> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveAllTags(data)
                        } catch (e: Exception) {}
                    }
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
            val apiResponse =  remoteDataSource.createTag(token, tag)
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
            val apiResponse =  remoteDataSource.updateTag(token, tag.id!!, tag)
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
            val apiResponse =  remoteDataSource.deleteTag(token, id)
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

    override suspend fun getListPet(forceRequest: Boolean): NetworkResult<List<PetDetailsDTO>> {
        if (!forceRequest) {
            val localPets = guardianLocalDataSourceImpl.getAllPets()
            if (localPets.isNotEmpty()) {
                return NetworkResult.Success(localPets)
            }
        }
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.getPetList(token)
            var result: NetworkResult<List<PetDetailsDTO>> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveAllPets(data)
                        } catch (e: Exception) {}
                    }
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

    override suspend fun getPetById(id: String): NetworkResult<PetDetailsDTO> {
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.getPetById(token, id)
            var result: NetworkResult<PetDetailsDTO> = NetworkResult.Error(0, null)
            apiResponse
                .onSuccess {
                    result = NetworkResult.Success(it)
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

    override suspend fun deletePetById(id: String): NetworkResult<Unit> {
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.deletePetById(token, id)
            var result: NetworkResult<Unit> = NetworkResult.Error(0, null)
            apiResponse
                .onSuccess {
                    result = NetworkResult.Success(it)
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

    override suspend fun updatePet(id: String, pet: PetCreateDTO, imageUri: String?): NetworkResult<PetDetailsDTO> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return try {
            val isLocalUri = imageUri != null && !imageUri.startsWith("http", ignoreCase = true)

            val imagePart: MultipartBody.Part = if (isLocalUri) {
                val imageFile = getFileFromUri(context = context, Uri.parse(imageUri))

                if (imageFile != null && imageFile.exists()) {
                    val mediaType = MediaType.parse("image/*")
                    val requestFile = RequestBody.create(mediaType, imageFile)
                    MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
                } else {
                    throw IllegalArgumentException("Falha ao processar a nova imagem")
                }
            } else if (imageUri != null && imageUri.startsWith("http", ignoreCase = true)) {
                val urlBody = RequestBody.create(MediaType.parse("text/plain"), imageUri)
                MultipartBody.Part.createFormData("image", "", urlBody)
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

            val apiResponse = remoteDataSource.updatePet(
                token = token,
                image = imagePart,
                specieName = specieNamePart,
                petName = petNamePart,
                gender = genderPart,
                breedName = breedNamePart,
                size = sizePart,
                castrated = castratedPart,
                dateOfBirth = dateOfBirthPart,
                id = id
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


    override suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>> {
        val localListPetSizes =
            guardianLocalDataSourceImpl.getListPetSizes(petSpecie)?.success?.data
        return if (!localListPetSizes.isNullOrEmpty()) {
            NetworkResult.Success(localListPetSizes)
        } else {
            val token = getToken()
            when (val apiResult = remoteDataSource.getListPetSizes(token!!, petSpecie)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveListPetSizes(petSpecie, apiResult.data)
                        } catch (e: Exception) {
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
            val token = getToken()
            when (val apiResult = remoteDataSource.getListPetRaces(token!!, petSpecie)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveListPetRaces(petSpecie, apiResult.data)
                        } catch (e: Exception) {
                        }
                    }

                    NetworkResult.Success(apiResult.data)
                }

                else -> apiResult
            }
        }
    }

    override suspend fun scheduled(item: TaskDTO): NetworkResult<Unit> {
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.scheduled(token, item)
            var result: NetworkResult<Unit> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess {
                    result = NetworkResult.Success(Unit)
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

    override suspend fun listTasksByPeriod(
        startDate: String,
        endDate: String,
        forceRequest: Boolean
    ): NetworkResult<PaginatedScheduleResponseDTO> {
        if (!forceRequest) {
            val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(startDate, endDate)
            if (localTasks.isNotEmpty()) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(localTasks, 1, 0, localTasks.size))
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        
        val start = java.time.LocalDateTime.parse(startDate.split(".")[0].replace("Z", ""))
        val end = java.time.LocalDateTime.parse(endDate.split(".")[0].replace("Z", ""))
        val daysBetween = java.time.Duration.between(start, end).toDays()

        val apiResponse = when {
            daysBetween <= 1 -> remoteDataSource.getTaskListCurrentDate(token)
            daysBetween <= 7 -> remoteDataSource.getTaskListCurrentWeek(token)
            else -> remoteDataSource.getTaskListCurrentMonth(token)
        }

        return handleTaskResponse(apiResponse, startDate, endDate)
    }

    override suspend fun listCurrentDateScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        val today = java.time.LocalDate.now()
        val start = today.atStartOfDay().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val end = today.atTime(java.time.LocalTime.MAX).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        if (!forceRequest) {
            val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(start, end)
            if (localTasks.isNotEmpty()) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(localTasks, 1))
            }
        }
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.getTaskListCurrentDate(token)
            return handleTaskResponse(apiResponse, start, end)
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun listCurrentWeekScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        val today = java.time.LocalDate.now()
        val start = today.with(java.time.DayOfWeek.MONDAY).atStartOfDay().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val end = today.with(java.time.DayOfWeek.SUNDAY).atTime(java.time.LocalTime.MAX).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        if (!forceRequest) {
            val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(start, end)
            if (localTasks.isNotEmpty()) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(localTasks, 1))
            }
        }
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.getTaskListCurrentWeek(token)
            return handleTaskResponse(apiResponse, start, end)
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    override suspend fun listCurrentMonthScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        val today = java.time.LocalDate.now()
        val start = today.withDayOfMonth(1).atStartOfDay().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val end = today.withDayOfMonth(today.lengthOfMonth()).atTime(java.time.LocalTime.MAX).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        if (!forceRequest) {
            val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(start, end)
            if (localTasks.isNotEmpty()) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(localTasks, 1))
            }
        }
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.getTaskListCurrentMonth(token)
            return handleTaskResponse(apiResponse, start, end)
        }.run {
            return NetworkResult.Exception(Throwable("Token não encontrado"))
        }
    }

    private suspend fun handleTaskResponse(
        apiResponse: NetworkResult<PaginatedScheduleResponseDTO>,
        startDate: String,
        endDate: String
    ): NetworkResult<PaginatedScheduleResponseDTO> {
        var result: NetworkResult<PaginatedScheduleResponseDTO> = NetworkResult.Error(0, null)
        apiResponse
            .onSuccess {
                coroutineScope {
                    try {
                        guardianLocalDataSourceImpl.saveAllTasks(it.data)
                    } catch (e: Exception) {
                    }
                }
                
                // Força o retorno dos dados filtrados pelo período solicitado, 
                // garantindo que o que veio da API respeite os limites de data da UI.
                val filteredLocal = guardianLocalDataSourceImpl.getTasksInPeriod(startDate, endDate)
                result = NetworkResult.Success(PaginatedScheduleResponseDTO(filteredLocal, 1, 0, filteredLocal.size))
            }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { throwable -> result = NetworkResult.Exception(throwable) }
        return result
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

            val apiResponse = remoteDataSource.createPet(
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
            null
        }
    }
}
