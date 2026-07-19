package com.petjournal.database.repository

import com.petjournal.database.database.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.petjournal.database.converter.Converter.toEntity
import com.petjournal.database.converter.Converter.toListPetRaceEntity
import com.petjournal.database.converter.Converter.toListPetRaceModel
import com.petjournal.database.converter.Converter.toListPetSizeEntity
import com.petjournal.database.converter.Converter.toListPetSizeItemModel
import com.petjournal.database.database.dao.ApplicationInformationDao
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.dao.PetDetailsDao
import com.petjournal.database.database.dao.TagDao
import com.petjournal.database.database.dao.TaskDao
import com.petjournal.database.database.entity.ApplicationInformation
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.PetDetailsEntity
import com.petjournal.database.database.entity.TagEntity
import com.petjournal.database.database.entity.TaskEntity
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.taskModel.ScheduleDataDTO
import com.soujunior.domain.repository.database.LocalDataSource
import com.soujunior.domain.use_case.base.DataResult

class LocalDataSourceImpl(
    private val guardianDao: GuardianProfileDao,
    private val appInfoDao: ApplicationInformationDao,
    private val petDetailsDao: PetDetailsDao,
    private val tagDao: TagDao,
    private val taskDao: TaskDao,
    private val appDatabase: AppDatabase,
) : LocalDataSource {

    override suspend fun getGuardianName(): String? {
        val profile = guardianDao.getProfile(1)
        return profile?.firstName
    }

    override suspend fun getGuardianEmail(): String? {
        return guardianDao.getProfile(1)?.email
    }

    override suspend fun saveGuardianName(response: GuardianNameResponse) {
        val existing = guardianDao.getProfile(1)
        if (existing == null) {
            guardianDao.insertProfile(
                GuardianProfile(
                    id = 1,
                    firstName = response.firstName,
                    lastName = response.lastName
                )
            )
            appInfoDao.insertInformation(ApplicationInformation(1, false))
        } else {
            val updatedProfile = existing.copy(
                firstName = response.firstName,
                lastName = response.lastName
            )
            guardianDao.insertProfile(updatedProfile)
        }
    }

    override suspend fun saveGuardianContact(email: String, phone: String) {
        val existing = guardianDao.getProfile(1)
        if (existing == null) {
            guardianDao.insertProfile(
                GuardianProfile(
                    id = 1,
                    email = email,
                    phone = phone
                )
            )
            appInfoDao.insertInformation(ApplicationInformation(1, false))
        } else {
            guardianDao.insertProfile(
                existing.copy(
                    email = email,
                    phone = phone
                )
            )
        }
    }

    override suspend fun savePetInformation(petModel: PetModel): DataResult<Long> {
        return try {
            DataResult.Success(
                guardianDao.insertPetInformation(petModel.toEntity())
            )
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }

    }

    override suspend fun getPetInformation(id: Long): DataResult<PetModel> {
        return try {
            DataResult.Success(guardianDao.getPetInformation(id))
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun updatePetInformation(petModel: PetModel): DataResult<Unit> {
        return try {
            DataResult.Success(guardianDao.updatePetInformation(petModel.toEntity()))
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun getListPetSizes(tag: String): DataResult<List<PetSizeItemModel>>? {
        return try {
            val data = guardianDao.getListPetSizes(tag)
            if (data != null) {
                DataResult.Success(data.toListPetSizeItemModel())
            } else {
                DataResult.Success(emptyList())
            }
        } catch (error: Throwable) {
            DataResult.Failure(error)
        }
    }


    override suspend fun saveListPetSizes(
        tag: String,
        listPetSize: List<PetSizeItemModel>
    ): DataResult<String> {
        return try {
            guardianDao.insertListPetSizes(listPetSize.toListPetSizeEntity(tag))
            DataResult.Success(tag)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }

    }

    override suspend fun getListPetRaces(tag: String): DataResult<List<PetRaceItemModel>>? {
        return try {
            val data = guardianDao.getListPetRaces(tag)
            if (data != null) {
                DataResult.Success(data.toListPetRaceModel())
            } else {
                DataResult.Success(emptyList())
            }
        } catch (error: Throwable) {
            DataResult.Failure(error)
        }
    }

    override suspend fun saveListPetRaces(
       tag: String, listPetRace: List<PetRaceItemModel>
    ): DataResult<String> {
        return try {
            guardianDao.insertListPetRaces(listPetRace.toListPetRaceEntity(tag))
            DataResult.Success(tag)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun deleteDatabase() {
        withContext(Dispatchers.IO) {
            appDatabase.clearAllTables()
        }
    }

    override suspend fun getAllPets(): List<PetDetailsDTO> {
        return petDetailsDao.getAllPets().map {
            PetDetailsDTO(
                id = it.id,
                guardianId = it.guardianId,
                specie = it.specie,
                specieAlias = it.specieAlias,
                petName = it.petName,
                gender = it.gender,
                breedAlias = it.breedAlias,
                breed = it.breed,
                size = it.size,
                castrated = it.castrated,
                dateOfBirth = it.dateOfBirth,
                image = it.image
            )
        }
    }

    override suspend fun saveAllPets(pets: List<PetDetailsDTO>) {
        petDetailsDao.insertAll(pets.map {
            PetDetailsEntity(
                id = it.id ?: "",
                guardianId = it.guardianId,
                specie = it.specie,
                specieAlias = it.specieAlias,
                petName = it.petName,
                gender = it.gender,
                breedAlias = it.breedAlias,
                breed = it.breed,
                size = it.size,
                castrated = it.castrated,
                dateOfBirth = it.dateOfBirth,
                image = it.image
            )
        })
    }

    override suspend fun deletePetById(id: String) {
        petDetailsDao.deleteById(id)
    }

    override suspend fun getAllTags(): List<TagDTO> {
        return tagDao.getAllTags().map {
            TagDTO(
                id = it.id,
                guardianId = it.guardianId,
                name = it.name,
                color = it.color
            )
        }
    }

    override suspend fun saveAllTags(tags: List<TagDTO>) {
        tagDao.insertAll(tags.map {
            TagEntity(
                id = it.id ?: "",
                guardianId = it.guardianId,
                name = it.name,
                color = it.color
            )
        })
    }

    override suspend fun getAllTasks(): List<ScheduleDataDTO> {
        return taskDao.getAllTasks().mapNotNull {
            it.scheduler?.let { scheduler ->
                ScheduleDataDTO(
                    id = it.id,
                    schedulerId = it.schedulerId,
                    start = it.start,
                    end = it.end,
                    scheduler = scheduler
                )
            }
        }
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAll()
    }

    override suspend fun getTasksInPeriod(startDate: String, endDate: String): List<ScheduleDataDTO> {
        val deleteThresholdRaw = java.time.Instant.now().minus(2, java.time.temporal.ChronoUnit.DAYS).toString()
        val deleteThreshold = cleanToUtcString(deleteThresholdRaw) ?: ""
        taskDao.deletePastTasks(deleteThreshold)

        val startClean = if (startDate.contains("T")) {
            startDate.split(".")[0].replace("Z", "")
        } else {
            "${startDate}T00:00:00"
        }
        val endClean = if (endDate.contains("T")) {
            endDate.split(".")[0].replace("Z", "")
        } else {
            "${endDate}T23:59:59"
        }
        
        val zoneId = java.time.ZoneId.systemDefault()
        val localStart = java.time.LocalDateTime.parse(startClean)
        val localEnd = java.time.LocalDateTime.parse(endClean)
        
        val startUtc = cleanToUtcString(localStart.atZone(zoneId).toInstant().toString()) ?: ""
        val endUtc = cleanToUtcString(localEnd.atZone(zoneId).toInstant().toString()) ?: ""

        val startDateTime = java.time.LocalDateTime.parse(startClean)
        val dayOfWeek = (startDateTime.dayOfWeek.value % 7).toString()
        val dayOfMonth = startDateTime.dayOfMonth.toString()

        return taskDao.getTasksInPeriod(startUtc, endUtc, dayOfWeek, dayOfMonth).mapNotNull {
            it.scheduler?.let { scheduler ->
                ScheduleDataDTO(
                    id = it.id,
                    schedulerId = it.schedulerId,
                    start = it.start,
                    end = it.end,
                    scheduler = scheduler
                )
            }
        }
    }

    override suspend fun getLocalTasksByPeriod(startDate: String, endDate: String, considerTime: Boolean): List<ScheduleDataDTO> {
        val currentDateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val currentClean = currentDateTime.split(".")[0].replace("Z", "")
        
        if (considerTime) {
            val deleteThresholdRaw = java.time.Instant.now().minus(2, java.time.temporal.ChronoUnit.DAYS).toString()
            val deleteThreshold = cleanToUtcString(deleteThresholdRaw) ?: ""
            taskDao.deletePastTasks(deleteThreshold)
        }

        var startClean = if (startDate.contains("T")) {
            startDate.split(".")[0].replace("Z", "")
        } else {
            "${startDate}T00:00:00"
        }
        val endClean = if (endDate.contains("T")) {
            endDate.split(".")[0].replace("Z", "")
        } else {
            "${endDate}T23:59:59"
        }
        
        if (considerTime && startClean < currentClean) {
            startClean = currentClean
        }

        val zoneId = java.time.ZoneId.systemDefault()
        val localStart = java.time.LocalDateTime.parse(startClean)
        val localEnd = java.time.LocalDateTime.parse(endClean)
        
        val startUtc = cleanToUtcString(localStart.atZone(zoneId).toInstant().toString()) ?: ""
        val endUtc = cleanToUtcString(localEnd.atZone(zoneId).toInstant().toString()) ?: ""

        val startDateTime = java.time.LocalDateTime.parse(startClean)
        val dayOfWeek = (startDateTime.dayOfWeek.value % 7).toString() // 0-6 (dom-sab)
        val dayOfMonth = startDateTime.dayOfMonth.toString()

        return taskDao.getTasksInPeriod(startUtc, endUtc, dayOfWeek, dayOfMonth).mapNotNull {
            it.scheduler?.let { scheduler ->
                ScheduleDataDTO(
                    id = it.id,
                    schedulerId = it.schedulerId,
                    start = it.start,
                    end = it.end,
                    scheduler = scheduler
                )
            }
        }
    }

    override suspend fun saveAllTasks(tasks: List<ScheduleDataDTO>) {
        val ids = tasks.mapNotNull { it.id }
        val existingTasks = if (ids.isNotEmpty()) taskDao.getTasksByIds(ids).associateBy { it.id } else emptyMap()
        
        val entities = tasks.map {
            val existing = existingTasks[it.id]
            val scheduler = it.scheduler
            TaskEntity(
                id = it.id ?: "",
                schedulerId = it.schedulerId,
                title = scheduler.title ?: "",
                description = scheduler.description,
                note = scheduler.note,
                start = cleanToUtcString(it.start),
                end = cleanToUtcString(it.end),
                isRecurrent = scheduler.daily == true || !scheduler.daysOfWeek.isNullOrEmpty() || !scheduler.daysOfMonth.isNullOrEmpty(),
                recurrenceType = when {
                    scheduler.daily == true -> "DAILY"
                    !scheduler.daysOfWeek.isNullOrEmpty() -> "WEEKLY"
                    !scheduler.daysOfMonth.isNullOrEmpty() -> "MONTHLY"
                    else -> null
                },
                daysOfWeek = scheduler.daysOfWeek?.joinToString(","),
                daysOfMonth = scheduler.daysOfMonth?.joinToString(","),
                tagId = scheduler.tagId,
                scheduler = scheduler,
                isAlarmScheduled = existing?.isAlarmScheduled ?: false
            )
        }
        taskDao.insertAll(entities)
    }

    override suspend fun getTasksToSchedule(): List<ScheduleDataDTO> {
        val tasks = taskDao.getTasksToSchedule()
        return tasks.mapNotNull {
            it.scheduler?.let { scheduler ->
                ScheduleDataDTO(
                    id = it.id,
                    schedulerId = it.schedulerId,
                    start = it.start,
                    end = it.end,
                    scheduler = scheduler
                )
            } ?: run {
                null
            }
        }
    }

    override suspend fun updateAlarmStatus(id: String, isScheduled: Boolean) {
        taskDao.updateAlarmStatus(id, isScheduled)
    }

    private fun cleanToUtcString(dateString: String?): String? {
        if (dateString == null) return null
        val clean = dateString.split(".")[0]
        return if (clean.endsWith("Z")) clean else "${clean}Z"
    }

    override suspend fun deleteTaskById(id: String) {
        taskDao.deleteTaskById(id)
    }

    override suspend fun deleteTasksBySchedulerId(schedulerId: String) {
        taskDao.deleteTasksBySchedulerId(schedulerId)
    }
}
