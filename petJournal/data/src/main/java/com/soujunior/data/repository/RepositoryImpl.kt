package com.soujunior.data.repository

import android.content.Context
import android.net.Uri
import com.soujunior.data.remote.RemoteDataSource
import com.soujunior.data.util.manager.JwtManager
import android.util.Log
import com.soujunior.domain.mapper.Mapper.toDomain
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
import com.soujunior.domain.repository.task.TaskReminderScheduler
import com.soujunior.domain.use_case.base.DataResult
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val guardianLocalDataSourceImpl: LocalDataSource,
    private val context: Context,
    private val taskReminderScheduler: TaskReminderScheduler
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
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        return when (val apiResult = remoteDataSource.getGuardianName(token)) {
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

    override suspend fun createPet(pet: PetCreateDTO, imageUri: String?): NetworkResult<PetDetailsDTO> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return try {
            val imagePart: MultipartBody.Part = if (imageUri != null) {
                val imageFile = getFileFromUri(context = context, Uri.parse(imageUri))
                if (imageFile != null && imageFile.exists()) {
                    val mediaType = MediaType.parse("image/*")
                    val requestFile = RequestBody.create(mediaType, imageFile)
                    MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
                } else {
                    throw IllegalArgumentException("Falha ao processar a imagem")
                }
            } else {
                throw IllegalArgumentException("Imagem é obrigatória")
            }

            val apiResponse = remoteDataSource.createPet(
                token = token,
                image = imagePart,
                specieName = (pet.specieName ?: "").toTextRequestBody(),
                petName = (pet.petName ?: "").toTextRequestBody(),
                gender = (pet.gender ?: "").toTextRequestBody(),
                breedName = (pet.breedName ?: "").toTextRequestBody(),
                size = (pet.size ?: "").toTextRequestBody(),
                castrated = (pet.castrated ?: false).toString().toTextRequestBody(),
                dateOfBirth = (pet.dateOfBirth ?: "").toTextRequestBody()
            )

            var result: NetworkResult<PetDetailsDTO> = NetworkResult.Error(0, null)
            apiResponse
                .onSuccess { result = NetworkResult.Success(it) }
                .onError { code, body -> result = NetworkResult.Error(code, body) }
                .onException { result = NetworkResult.Exception(it) }
            result
        } catch (e: Exception) {
            NetworkResult.Exception(e)
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
                val urlBody = RequestBody.create(MediaType.parse("text/plain"), imageUri!!)
                MultipartBody.Part.createFormData("image", "", urlBody)
            } else {
                throw IllegalArgumentException("Imagem é obrigatória")
            }

            val apiResponse = remoteDataSource.updatePet(
                token = token,
                image = imagePart,
                specieName = (pet.specieName ?: "").toTextRequestBody(),
                petName = (pet.petName ?: "").toTextRequestBody(),
                gender = (pet.gender ?: "").toTextRequestBody(),
                breedName = (pet.breedName ?: "").toTextRequestBody(),
                size = (pet.size ?: "").toTextRequestBody(),
                castrated = (pet.castrated ?: false).toString().toTextRequestBody(),
                dateOfBirth = (pet.dateOfBirth ?: "").toTextRequestBody(),
                id = id
            )

            var result: NetworkResult<PetDetailsDTO> = NetworkResult.Error(0, null)
            apiResponse
                .onSuccess { result = NetworkResult.Success(it) }
                .onError { code, body -> result = NetworkResult.Error(code, body) }
                .onException { result = NetworkResult.Exception(it) }
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
            val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
            when (val apiResult = remoteDataSource.getListPetSizes(token, petSpecie)) {
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
            val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
            when (val apiResult = remoteDataSource.getListPetRaces(token, petSpecie)) {
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

    override suspend fun saveTaskLocal(task: TaskDTO): DataResult<Unit> {
        return try {
            val tags = guardianLocalDataSourceImpl.getAllTags()
            val pets = guardianLocalDataSourceImpl.getAllPets()

            val tag = tags.find { it.id == task.tagId } ?: return DataResult.Failure(Exception("Tag not found locally"))
            val selectedPets = pets.filter { task.pets?.contains(it.id) == true }

            val schedulerDto = com.soujunior.domain.model.taskModel.SchedulerDTO(
                id = java.util.UUID.randomUUID().toString(),
                tagId = tag.id,
                title = task.title,
                description = task.description,
                note = task.note,
                startAt = task.startAt,
                endAt = task.endAt,
                daysOfWeek = task.daysOfWeek,
                daily = null,
                daysOfMonth = null,
                tag = tag,
                pets = selectedPets
            )

            val scheduleDataDto = com.soujunior.domain.model.taskModel.ScheduleDataDTO(
                id = java.util.UUID.randomUUID().toString(),
                schedulerId = schedulerDto.id,
                start = task.startAt,
                end = task.endAt,
                scheduler = schedulerDto
            )

            guardianLocalDataSourceImpl.saveAllTasks(listOf(scheduleDataDto))

            Log.d("RepositoryImpl", "⚙️ [PROCESSANDO] Agendando ID recém-criado: ${scheduleDataDto.id} | Título: ${scheduleDataDto.scheduler.title}")
            val domainTask = scheduleDataDto.toDomain()
            taskReminderScheduler.schedule(domainTask)
            
            scheduleDataDto.id?.let { id ->
                guardianLocalDataSourceImpl.updateAlarmStatus(id, true)
            }

            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }

    override suspend fun scheduled(item: TaskDTO): NetworkResult<Unit> {
        getToken()?.let { token ->
            val apiResponse =  remoteDataSource.scheduled(token, item)
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

    override suspend fun listTasksByPeriod(
        startDate: String,
        endDate: String,
        forceRequest: Boolean
    ): NetworkResult<PaginatedScheduleResponseDTO> {
        if (!forceRequest) {
            val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(startDate, endDate)
            if (localTasks.isNotEmpty()) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
            }
        }
        getToken()?.let { token ->
            val apiResponseGeneric = remoteDataSource.getTaskListCurrentDate(token) 
            
            var result: NetworkResult<PaginatedScheduleResponseDTO> = NetworkResult.Error(0, null)

            apiResponseGeneric
                .onSuccess { data ->
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveAllTasks(data.data)
                            data.data.forEach { scheduleDataDto ->
                                val domainTask = scheduleDataDto.toDomain()
                                taskReminderScheduler.schedule(domainTask)
                                scheduleDataDto.id?.let { id ->
                                    guardianLocalDataSourceImpl.updateAlarmStatus(id, true)
                                }
                            }
                        } catch (e: Exception) {
                        }
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

    override suspend fun listCurrentDateScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        if (!forceRequest) {
            val today = LocalDate.now().toString()
            val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(today, today)
            if (localTasks.isNotEmpty()) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
            }
        }

        getToken()?.let { token ->
            val apiResponse = remoteDataSource.getTaskListCurrentDate(token)
            var result: NetworkResult<PaginatedScheduleResponseDTO> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveAllTasks(data.data)
                            data.data.forEach { scheduleDataDto ->
                                val domainTask = scheduleDataDto.toDomain()
                                taskReminderScheduler.schedule(domainTask)
                                scheduleDataDto.id?.let { id ->
                                    guardianLocalDataSourceImpl.updateAlarmStatus(id, true)
                                }
                            }
                        } catch (e: Exception) {
                        }
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

    override suspend fun listCurrentWeekScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        if (!forceRequest) {
            val today = LocalDate.now()
            val sunday = today.minusDays(today.dayOfWeek.value % 7L)
            val saturday = sunday.plusDays(6)
            val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(sunday.toString(), saturday.toString())
            if (localTasks.isNotEmpty()) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
            }
        }
        getToken()?.let { token ->
            val apiResponse = remoteDataSource.getTaskListCurrentWeek(token)
            var result: NetworkResult<PaginatedScheduleResponseDTO> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveAllTasks(data.data)
                            data.data.forEach { scheduleDataDto ->
                                val domainTask = scheduleDataDto.toDomain()
                                taskReminderScheduler.schedule(domainTask)
                                scheduleDataDto.id?.let { id ->
                                    guardianLocalDataSourceImpl.updateAlarmStatus(id, true)
                                }
                            }
                        } catch (e: Exception) {
                        }
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

    override suspend fun listCurrentMonthScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        if (!forceRequest) {
            val today = LocalDate.now()
            val start = today.withDayOfMonth(1).toString()
            val end = today.withDayOfMonth(today.lengthOfMonth()).toString()
            val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(start, end)
            if (localTasks.isNotEmpty()) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
            }
        }
        getToken()?.let { token ->
            val apiResponse = remoteDataSource.getTaskListCurrentMonth(token)
            var result: NetworkResult<PaginatedScheduleResponseDTO> = NetworkResult.Error(0, null)

            apiResponse
                .onSuccess { data ->
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveAllTasks(data.data)
                            data.data.forEach { scheduleDataDto ->
                                val domainTask = scheduleDataDto.toDomain()
                                taskReminderScheduler.schedule(domainTask)
                                scheduleDataDto.id?.let { id ->
                                    guardianLocalDataSourceImpl.updateAlarmStatus(id, true)
                                }
                            }
                        } catch (e: Exception) {
                        }
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

    private fun String.toTextRequestBody(): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), this)
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val contentResolver = context.contentResolver
            val fileName = "pet_image_${System.currentTimeMillis()}.jpg"
            val tempFile = File(context.cacheDir, fileName)
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            tempFile
        } catch (e: Exception) {
            null
        }
    }
}
