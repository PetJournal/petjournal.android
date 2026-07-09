package com.soujunior.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.soujunior.data.util.ImageHelper
import com.soujunior.data.remote.RemoteDataSource
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.data.util.manager.SyncDataManager
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
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.model.taskModel.PaginatedNextEventsResponseDTO
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseDTO
import com.soujunior.domain.model.taskModel.ScheduleDataDTO
import com.soujunior.domain.model.taskModel.SchedulerDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.network.onError
import com.soujunior.domain.network.onException
import com.soujunior.domain.network.onSuccess
import com.soujunior.domain.repository.database.LocalDataSource
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.repository.task.TaskReminderScheduler
import com.soujunior.domain.use_case.base.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.time.LocalDate
import java.util.UUID

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val guardianLocalDataSourceImpl: LocalDataSource,
    private val context: Context,
    private val taskReminderScheduler: TaskReminderScheduler
) : Repository {

    private val jwtManager: JwtManager = JwtManager.getInstance(context)
    private val syncDataManager: SyncDataManager = SyncDataManager.getInstance(context)

    companion object {
        private const val CACHE_TIMEOUT_MILLIS = 15 * 60 * 1000L
    }

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

    override suspend fun getGuardianName(forceRequest: Boolean, localOnly: Boolean): NetworkResult<GuardianNameResponse> {
        val localName = guardianLocalDataSourceImpl.getGuardianName()
        val localEmpty = localName.isNullOrBlank()

        if (localOnly) {
            return NetworkResult.Success(GuardianNameResponse(localName ?: "", ""))
        }

        if (!forceRequest && !localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.GUARDIAN_NAME).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(GuardianNameResponse(localName!!, ""))
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        val apiResult = remoteDataSource.getGuardianName(token)
        val finalResult = if (apiResult is NetworkResult.Error && apiResult.code == 404) {
            remoteDataSource.getGuardianProfile(token)
        } else {
            apiResult
        }

        return when (finalResult) {
            is NetworkResult.Success -> {
                val data = finalResult.data
                val guardianNameResponse = when (data) {
                    is UserInfoResponse -> GuardianNameResponse(data.firstName, data.lastName)
                    is GuardianNameResponse -> data
                    else -> GuardianNameResponse("", "")
                }
                guardianLocalDataSourceImpl.saveGuardianName(guardianNameResponse)
                if (data is UserInfoResponse) {
                    guardianLocalDataSourceImpl.saveGuardianContact(data.email, data.phone)
                }
                syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.GUARDIAN_NAME)

                val updatedLocal = guardianLocalDataSourceImpl.getGuardianName()
                NetworkResult.Success(if (!updatedLocal.isNullOrBlank()) GuardianNameResponse(updatedLocal, "") else guardianNameResponse)
            }
            is NetworkResult.Error -> {
                if (finalResult.code == 404) NetworkResult.Success(GuardianNameResponse("", ""))
                else if (!localEmpty) NetworkResult.Success(GuardianNameResponse(localName!!, ""))
                else NetworkResult.Error(finalResult.code, finalResult.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(GuardianNameResponse(localName!!, ""))
                else NetworkResult.Exception(finalResult.e)
            }
        }
    }

    override suspend fun getGuardianEmail(): String? = guardianLocalDataSourceImpl.getGuardianEmail()

    override suspend fun saveGuardianContact(email: String, phone: String) = guardianLocalDataSourceImpl.saveGuardianContact(email, phone)

    override suspend fun savePet(petModel: PetModel): DataResult<Long> {
        val guardianId = 1
        val petInformation = petModel.copy(species = petModel.species, guardianId = guardianId)
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.savePetInformation(petInformation).success.data)
        } catch (e: Throwable) { DataResult.Failure(e) }
    }

    override suspend fun getListSize(animal: String): NetworkResult<List<SizeDTO>> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        var result: NetworkResult<List<SizeDTO>> = NetworkResult.Error(0, null)
        remoteDataSource.getListSize(token, animal)
            .onSuccess { result = NetworkResult.Success(it) }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun getListBreed(animal: String): NetworkResult<List<BreedDTO>> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        var result: NetworkResult<List<BreedDTO>> = NetworkResult.Error(0, null)
        remoteDataSource.getListBreeds(token, animal)
            .onSuccess { result = NetworkResult.Success(it) }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun getListTag(forceRequest: Boolean, localOnly: Boolean): NetworkResult<List<TagDTO>> {
        val localTags = guardianLocalDataSourceImpl.getAllTags()
        if (localOnly) {
            return NetworkResult.Success(localTags)
        }
        val localEmpty = localTags.isEmpty()

        if (!forceRequest && !localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.LIST_TAG).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(localTags)
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResponse = remoteDataSource.getListTag(token)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveAllTags(apiResponse.data)
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.LIST_TAG)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getAllTags()
                NetworkResult.Success(if (updatedLocal.isNotEmpty()) updatedLocal else apiResponse.data)
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(localTags)
                else NetworkResult.Error(apiResponse.code, apiResponse.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(localTags)
                else NetworkResult.Exception(apiResponse.e)
            }
        }
    }

    override suspend fun createTag(tag: TagDTO): NetworkResult<TagDTO> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        var result: NetworkResult<TagDTO> = NetworkResult.Error(0, null)
        remoteDataSource.createTag(token, tag)
            .onSuccess { 
                result = NetworkResult.Success(it) 
                try {
                    val localTags = guardianLocalDataSourceImpl.getAllTags().toMutableList()
                    localTags.add(it)
                    guardianLocalDataSourceImpl.saveAllTags(localTags)
                } catch (e: Exception) {
                    Log.e("RepositoryImpl", "Erro ao salvar nova tag localmente", e)
                }
            }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun updateTag(tag: TagDTO): NetworkResult<UpdatePetByIdDTO> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        var result: NetworkResult<UpdatePetByIdDTO> = NetworkResult.Error(0, null)
        remoteDataSource.updateTag(token, tag.id!!, tag)
            .onSuccess { result = NetworkResult.Success(it) }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun deleteTag(id: String): NetworkResult<Unit> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        var result: NetworkResult<Unit> = NetworkResult.Error(0, null)
        remoteDataSource.deleteTag(token, id)
            .onSuccess { result = NetworkResult.Success(it) }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun getListPet(forceRequest: Boolean, localOnly: Boolean): NetworkResult<List<PetDetailsDTO>> {
        val localPets = guardianLocalDataSourceImpl.getAllPets()
        if (localOnly) {
            return NetworkResult.Success(localPets)
        }
        val localEmpty = localPets.isEmpty()

        if (!forceRequest && !localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.LIST_PET).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(localPets)
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResponse = remoteDataSource.getPetList(token)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveAllPets(apiResponse.data)
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.LIST_PET)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getAllPets()
                NetworkResult.Success(if (updatedLocal.isNotEmpty()) updatedLocal else apiResponse.data)
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(localPets)
                else NetworkResult.Error(apiResponse.code, apiResponse.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(localPets)
                else NetworkResult.Exception(apiResponse.e)
            }
        }
    }

    override suspend fun getPet(idPet: Long): DataResult<PetModel> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.getPetInformation(idPet).success.data)
        } catch (e: Throwable) { DataResult.Failure(e) }
    }

    override suspend fun getPetById(id: String): NetworkResult<PetDetailsDTO> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        var result: NetworkResult<PetDetailsDTO> = NetworkResult.Error(0, null)
        remoteDataSource.getPetById(token, id)
            .onSuccess { result = NetworkResult.Success(it) }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun deletePetById(id: String): NetworkResult<Unit> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        var result: NetworkResult<Unit> = NetworkResult.Error(0, null)
        remoteDataSource.deletePetById(token, id)
            .onSuccess { 
                result = NetworkResult.Success(it) 
                try {
                    guardianLocalDataSourceImpl.deletePetById(id)
                } catch (e: Exception) {
                    Log.e("RepositoryImpl", "Erro ao deletar pet localmente", e)
                }
            }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun createPet(pet: PetCreateDTO, imageUri: String?): NetworkResult<PetDetailsDTO> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        return try {
            val imagePart: MultipartBody.Part? = if (imageUri != null) {
                val imageFile = ImageHelper.getFileFromUri(context = context, Uri.parse(imageUri))
                if (imageFile != null && imageFile.exists()) {
                    val mediaType = MediaType.parse("image/*")
                    val requestFile = RequestBody.create(mediaType, imageFile)
                    MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
                } else throw IllegalArgumentException("Falha ao processar a imagem")
            } else null

            val apiResponse = remoteDataSource.createPet(
                token = token, image = imagePart, specieName = (pet.specieName ?: "").toTextRequestBody(),
                petName = (pet.petName ?: "").toTextRequestBody(), gender = (pet.gender ?: "").toTextRequestBody(),
                breedName = (pet.breedName ?: "").toTextRequestBody(), size = (pet.size ?: "").toTextRequestBody(),
                castrated = (pet.castrated ?: false).toString().toTextRequestBody(), dateOfBirth = (pet.dateOfBirth ?: "").toTextRequestBody()
            )

            var result: NetworkResult<PetDetailsDTO> = NetworkResult.Error(0, null)
            apiResponse.onSuccess { 
                result = NetworkResult.Success(it) 
                try {
                    val localPets = guardianLocalDataSourceImpl.getAllPets().toMutableList()
                    localPets.add(it)
                    guardianLocalDataSourceImpl.saveAllPets(localPets)
                } catch (e: Exception) {
                    Log.e("RepositoryImpl", "Erro ao salvar novo pet localmente", e)
                }
            }.onError { code, body -> result = NetworkResult.Error(code, body) }.onException { result = NetworkResult.Exception(it) }
            result
        } catch (e: Exception) { NetworkResult.Exception(e) }
    }

    override suspend fun updatePet(id: String, pet: PetCreateDTO, imageUri: String?): NetworkResult<PetDetailsDTO> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        return try {
            val isLocalUri = imageUri != null && !imageUri.startsWith("http", ignoreCase = true)
            val imagePart: MultipartBody.Part? = if (isLocalUri) {
                val imageFile = ImageHelper.getFileFromUri(context = context, Uri.parse(imageUri))
                if (imageFile != null && imageFile.exists()) {
                    val mediaType = MediaType.parse("image/*")
                    val requestFile = RequestBody.create(mediaType, imageFile)
                    MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
                } else throw IllegalArgumentException("Falha ao processar a nova imagem")
            } else if (imageUri != null && imageUri.startsWith("http", ignoreCase = true)) {
                val urlBody = RequestBody.create(MediaType.parse("text/plain"), imageUri)
                MultipartBody.Part.createFormData("image", "", urlBody)
            } else null

            val apiResponse = remoteDataSource.updatePet(
                token = token, image = imagePart, specieName = (pet.specieName ?: "").toTextRequestBody(),
                petName = (pet.petName ?: "").toTextRequestBody(), gender = (pet.gender ?: "").toTextRequestBody(),
                breedName = (pet.breedName ?: "").toTextRequestBody(), size = (pet.size ?: "").toTextRequestBody(),
                castrated = (pet.castrated ?: false).toString().toTextRequestBody(), dateOfBirth = (pet.dateOfBirth ?: "").toTextRequestBody(), id = id
            )

            var result: NetworkResult<PetDetailsDTO> = NetworkResult.Error(0, null)
            apiResponse.onSuccess { 
                result = NetworkResult.Success(it) 
                try {
                    val localPets = guardianLocalDataSourceImpl.getAllPets().toMutableList()
                    val index = localPets.indexOfFirst { p -> p.id == id }
                    if (index != -1) {
                        localPets[index] = it
                    } else {
                        localPets.add(it)
                    }
                    guardianLocalDataSourceImpl.saveAllPets(localPets)
                } catch (e: Exception) {
                    Log.e("RepositoryImpl", "Erro ao atualizar pet localmente", e)
                }
            }.onError { code, body -> result = NetworkResult.Error(code, body) }.onException { result = NetworkResult.Exception(it) }
            result
        } catch (e: Exception) { NetworkResult.Exception(e) }
    }

    override suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>> {
        val localListPetSizes = guardianLocalDataSourceImpl.getListPetSizes(petSpecie)?.success?.data
        val localEmpty = localListPetSizes.isNullOrEmpty()

        if (!localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.LIST_PET_SIZES).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(localListPetSizes!!)
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResult = remoteDataSource.getListPetSizes(token, petSpecie)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveListPetSizes(petSpecie, apiResult.data)
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.LIST_PET_SIZES)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getListPetSizes(petSpecie)?.success?.data
                NetworkResult.Success(if (!updatedLocal.isNullOrEmpty()) updatedLocal else apiResult.data)
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(localListPetSizes!!)
                else NetworkResult.Error(apiResult.code, apiResult.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(localListPetSizes!!)
                else NetworkResult.Exception(apiResult.e)
            }
        }
    }

    override suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>> {
        val localListPetRaces = guardianLocalDataSourceImpl.getListPetRaces(petSpecie)?.success?.data
        val localEmpty = localListPetRaces.isNullOrEmpty()

        if (!localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.LIST_PET_RACES).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(localListPetRaces!!)
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResult = remoteDataSource.getListPetRaces(token, petSpecie)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveListPetRaces(petSpecie, apiResult.data)
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.LIST_PET_RACES)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getListPetRaces(petSpecie)?.success?.data
                NetworkResult.Success(if (!updatedLocal.isNullOrEmpty()) updatedLocal else apiResult.data)
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(localListPetRaces!!)
                else NetworkResult.Error(apiResult.code, apiResult.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(localListPetRaces!!)
                else NetworkResult.Exception(apiResult.e)
            }
        }
    }

    override suspend fun saveTaskLocal(task: TaskDTO): DataResult<Unit> {
        return try {
            val tags = guardianLocalDataSourceImpl.getAllTags()
            val pets = guardianLocalDataSourceImpl.getAllPets()

            val tag = tags.find { it.id == task.tagId } ?: return DataResult.Failure(Exception("Tag not found locally"))
            val selectedPets = pets.filter { task.pets?.contains(it.id) == true }

            val schedulerDto = SchedulerDTO(
                id = UUID.randomUUID().toString(),
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

            val scheduleDataDto = ScheduleDataDTO(
                id = UUID.randomUUID().toString(),
                schedulerId = schedulerDto.id,
                start = task.startAt,
                end = task.endAt,
                scheduler = schedulerDto
            )

            guardianLocalDataSourceImpl.saveAllTasks(listOf(scheduleDataDto))

            Log.d("RepositoryImpl", "⚙️ [PROCESSANDO] Agendando ID recém-criado: ${scheduleDataDto.id}")
            taskReminderScheduler.schedule(scheduleDataDto.toDomain())
            scheduleDataDto.id?.let { guardianLocalDataSourceImpl.updateAlarmStatus(it, true) }

            DataResult.Success(Unit)
        } catch (e: Exception) { DataResult.Failure(e) }
    }

    override suspend fun scheduled(item: TaskDTO): NetworkResult<Unit> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        var result: NetworkResult<Unit> = NetworkResult.Error(0, null)
        remoteDataSource.scheduled(token, item)
            .onSuccess { result = NetworkResult.Success(it) }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun listTasksByPeriod(
        startDate: String,
        endDate: String,
        forceRequest: Boolean,
        localOnly: Boolean
    ): NetworkResult<PaginatedScheduleResponseDTO> {
        val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(startDate, endDate)
        if (localOnly) {
            return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
        }
        val localEmpty = localTasks.isEmpty()

        if (!forceRequest && !localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.TASKS_PERIOD).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResponse = remoteDataSource.getTaskListCurrentDate(token)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveAllTasks(apiResponse.data.data)
                    apiResponse.data.data.forEach { scheduleDataDto ->
                        taskReminderScheduler.schedule(scheduleDataDto.toDomain())
                        scheduleDataDto.id?.let { guardianLocalDataSourceImpl.updateAlarmStatus(it, true) }
                    }
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.TASKS_PERIOD)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getTasksInPeriod(startDate, endDate)
                NetworkResult.Success(PaginatedScheduleResponseDTO(data = updatedLocal))
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
                else NetworkResult.Error(apiResponse.code, apiResponse.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
                else NetworkResult.Exception(apiResponse.e)
            }
        }
    }

    override suspend fun getLocalTasksByPeriod(startDate: String, endDate: String, considerTime: Boolean): DataResult<PaginatedScheduleResponseDTO> {
        return try {
            val localTasks = guardianLocalDataSourceImpl.getLocalTasksByPeriod(startDate, endDate, considerTime)
            DataResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
        } catch (e: Exception) { DataResult.Failure(e) }
    }

    override suspend fun listCurrentDateScheduled(forceRequest: Boolean, localOnly: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        val today = LocalDate.now().toString()
        val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(today, today)
        if (localOnly) {
            return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
        }
        val localEmpty = localTasks.isEmpty()

        if (!forceRequest && !localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.TASKS_PERIOD).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResponse = remoteDataSource.getTaskListCurrentDate(token)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveAllTasks(apiResponse.data.data)
                    apiResponse.data.data.forEach { scheduleDataDto ->
                        taskReminderScheduler.schedule(scheduleDataDto.toDomain())
                        scheduleDataDto.id?.let { guardianLocalDataSourceImpl.updateAlarmStatus(it, true) }
                    }
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.TASKS_PERIOD)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getTasksInPeriod(today, today)
                NetworkResult.Success(PaginatedScheduleResponseDTO(data = updatedLocal))
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
                else NetworkResult.Error(apiResponse.code, apiResponse.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
                else NetworkResult.Exception(apiResponse.e)
            }
        }
    }

    override suspend fun listCurrentWeekScheduled(forceRequest: Boolean, localOnly: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        val today = LocalDate.now()
        val sunday = today.minusDays(today.dayOfWeek.value % 7L).toString()
        val saturday = today.minusDays(today.dayOfWeek.value % 7L).plusDays(6).toString()
        val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(sunday, saturday)
        if (localOnly) {
            return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
        }
        val localEmpty = localTasks.isEmpty()

        if (!forceRequest && !localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.TASKS_PERIOD).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResponse = remoteDataSource.getTaskListCurrentWeek(token)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveAllTasks(apiResponse.data.data)
                    apiResponse.data.data.forEach { scheduleDataDto ->
                        taskReminderScheduler.schedule(scheduleDataDto.toDomain())
                        scheduleDataDto.id?.let { guardianLocalDataSourceImpl.updateAlarmStatus(it, true) }
                    }
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.TASKS_PERIOD)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getTasksInPeriod(sunday, saturday)
                NetworkResult.Success(PaginatedScheduleResponseDTO(data = updatedLocal))
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
                else NetworkResult.Error(apiResponse.code, apiResponse.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
                else NetworkResult.Exception(apiResponse.e)
            }
        }
    }

    override suspend fun listCurrentMonthScheduled(forceRequest: Boolean, localOnly: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        val today = LocalDate.now()
        val start = today.withDayOfMonth(1).toString()
        val end = today.withDayOfMonth(today.lengthOfMonth()).toString()
        val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(start, end)
        if (localOnly) {
            return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
        }
        val localEmpty = localTasks.isEmpty()

        if (!forceRequest && !localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.TASKS_PERIOD).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResponse = remoteDataSource.getTaskListCurrentMonth(token)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveAllTasks(apiResponse.data.data)
                    apiResponse.data.data.forEach { scheduleDataDto ->
                        taskReminderScheduler.schedule(scheduleDataDto.toDomain())
                        scheduleDataDto.id?.let { guardianLocalDataSourceImpl.updateAlarmStatus(it, true) }
                    }
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.TASKS_PERIOD)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getTasksInPeriod(start, end)
                NetworkResult.Success(PaginatedScheduleResponseDTO(data = updatedLocal))
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
                else NetworkResult.Error(apiResponse.code, apiResponse.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(PaginatedScheduleResponseDTO(data = localTasks))
                else NetworkResult.Exception(apiResponse.e)
            }
        }
    }

    override suspend fun getNextEventsForPet(petId: String, forceRequest: Boolean): NetworkResult<PaginatedNextEventsResponseDTO> {
        val today = LocalDate.now()
        val start = today.atStartOfDay().toString()
        val end = today.plusYears(1).atTime(java.time.LocalTime.MAX).toString()
        
        val localTasks = guardianLocalDataSourceImpl.getTasksInPeriod(start, end)
        val filteredLocalTasks = localTasks.filter { task ->
            task.scheduler.pets.any { pet -> pet.id == petId }
        }
        val localEmpty = filteredLocalTasks.isEmpty()

        if (!forceRequest && !localEmpty) {
            val lastSync = syncDataManager.getLastSyncTime(SyncDataManager.SyncKeys.TASKS_NEXT_PET).first() ?: 0L
            if (System.currentTimeMillis() - lastSync < CACHE_TIMEOUT_MILLIS) {
                return NetworkResult.Success(PaginatedNextEventsResponseDTO(nextEvents = filteredLocalTasks))
            }
        }

        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))

        return when (val apiResponse = remoteDataSource.getNextEventsForPet(token, petId)) {
            is NetworkResult.Success -> {
                try {
                    guardianLocalDataSourceImpl.saveAllTasks(apiResponse.data.nextEvents)
                    apiResponse.data.nextEvents.forEach { scheduleDataDto ->
                        taskReminderScheduler.schedule(scheduleDataDto.toDomain())
                        scheduleDataDto.id?.let { guardianLocalDataSourceImpl.updateAlarmStatus(it, true) }
                    }
                    syncDataManager.saveSyncTime(SyncDataManager.SyncKeys.TASKS_NEXT_PET)
                } catch (e: Exception) { Log.e("RepositoryImpl", "Erro local", e) }
                val updatedLocal = guardianLocalDataSourceImpl.getTasksInPeriod(start, end)
                val updatedFiltered = updatedLocal.filter { task ->
                    task.scheduler.pets.any { pet -> pet.id == petId }
                }
                NetworkResult.Success(PaginatedNextEventsResponseDTO(
                    nextEvents = updatedFiltered, 
                    page = apiResponse.data.page, 
                    limit = apiResponse.data.limit, 
                    totalPages = apiResponse.data.totalPages
                ))
            }
            is NetworkResult.Error -> {
                if (!localEmpty) NetworkResult.Success(PaginatedNextEventsResponseDTO(nextEvents = filteredLocalTasks))
                else NetworkResult.Error(apiResponse.code, apiResponse.body)
            }
            is NetworkResult.Exception -> {
                if (!localEmpty) NetworkResult.Success(PaginatedNextEventsResponseDTO(nextEvents = filteredLocalTasks))
                else NetworkResult.Exception(apiResponse.e)
            }
        }
    }

    private fun String.toTextRequestBody(): RequestBody = RequestBody.create(MediaType.parse("text/plain"), this)

    override suspend fun deleteOnlyThisTaskById(id: String): NetworkResult<Unit> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        try {
            guardianLocalDataSourceImpl.deleteTaskById(id)
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Erro ao deletar task localmente", e)
        }

        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.deleteOnlyThisTaskById(token, id)
                .onSuccess {
                    Log.d("RepositoryImpl", "deleteTasksById: Sucesso na API, deletado remotamente. ID = $id")
                }
                .onError { code, body -> 
                    Log.e("RepositoryImpl", "Erro remoto ao deletar task, código = $code")
                }
                .onException { e ->
                    Log.e("RepositoryImpl", "Exceção remota ao deletar task", e)
                }
        }
        
        return NetworkResult.Success(Unit)
        var result: NetworkResult<Unit> = NetworkResult.Error(0, null)
        remoteDataSource.deleteOnlyThisTaskById(token, id)
            .onSuccess {
                result = NetworkResult.Success(it)
                try {
                } catch (e: Exception) {
                }
            }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }

    override suspend fun deleteAllTheseTasksById(id: String): NetworkResult<Unit> {
        val token = getToken() ?: return NetworkResult.Exception(Throwable("Token não encontrado"))
        try {
            guardianLocalDataSourceImpl.deleteTaskById(id)
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Erro ao deletar task localmente", e)
        }

        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.deleteAllTasksById(token, id)
                .onSuccess {
                    Log.d("RepositoryImpl", "deleteTasksById: Sucesso na API, deletado remotamente. ID = $id")
                }
                .onError { code, body ->
                    Log.e("RepositoryImpl", "Erro remoto ao deletar task, código = $code")
                }
                .onException { e ->
                    Log.e("RepositoryImpl", "Exceção remota ao deletar task", e)
                }
        }

        return NetworkResult.Success(Unit)
        var result: NetworkResult<Unit> = NetworkResult.Error(0, null)
        remoteDataSource.deleteOnlyThisTaskById(token, id)
            .onSuccess {
                result = NetworkResult.Success(it)
                try {
                } catch (e: Exception) {
                }
            }
            .onError { code, body -> result = NetworkResult.Error(code, body) }
            .onException { result = NetworkResult.Exception(it) }
        return result
    }
}