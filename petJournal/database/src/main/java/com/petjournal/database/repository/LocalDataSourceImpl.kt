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
import android.util.Log
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
        return guardianDao.getProfile(1)?.firstName
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
            guardianDao.insertProfile(
                existing.copy(
                    firstName = response.firstName,
                    lastName = response.lastName
                )
            )
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

    override suspend fun getTasksInPeriod(startDate: String, endDate: String): List<ScheduleDataDTO> {
        val currentDateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val currentClean = currentDateTime.split(".")[0].replace("Z", "")
        taskDao.deletePastTasks(currentClean)

        val startClean = startDate.split(".")[0].replace("Z", "")
        val endClean = endDate.split(".")[0].replace("Z", "")
        
        val startDateTime = java.time.LocalDateTime.parse(startClean)
        val dayOfWeek = (startDateTime.dayOfWeek.value % 7).toString() // 0-6 (dom-sab)
        val dayOfMonth = startDateTime.dayOfMonth.toString()

        return taskDao.getTasksInPeriod(startClean, endClean, dayOfWeek, dayOfMonth).mapNotNull {
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
        
        // Se formos considerar a hora exata (para resgatar apenas as atuais/futuras e não as do passado), deletamos primeiro
        if (considerTime) {
            taskDao.deletePastTasks(currentClean)
        }

        var startClean = startDate.split(".")[0].replace("Z", "")
        val endClean = endDate.split(".")[0].replace("Z", "")
        
        if (considerTime && startClean < currentClean) {
            startClean = currentClean
        }

        val startDateTime = java.time.LocalDateTime.parse(startClean)
        val dayOfWeek = (startDateTime.dayOfWeek.value % 7).toString() // 0-6 (dom-sab)
        val dayOfMonth = startDateTime.dayOfMonth.toString()

        return taskDao.getTasksInPeriod(startClean, endClean, dayOfWeek, dayOfMonth).mapNotNull {
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
        Log.d("LocalDataSource", "💾 Salvando ${tasks.size} tarefas no banco local.")
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
                start = it.start, // Mantendo original para preservar UTC/Z
                end = it.end,
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
        Log.d("LocalDataSource", "✅ Inserção concluída.")
    }

    override suspend fun getTasksToSchedule(): List<ScheduleDataDTO> {
        val tasks = taskDao.getTasksToSchedule()
        Log.d("LocalDataSource", "🔍 getTasksToSchedule: Encontradas ${tasks.size} tarefas com isAlarmScheduled = 0")
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
                Log.w("LocalDataSource", "⚠️ Tarefa ${it.id} ignorada: scheduler nulo no banco.")
                null
            }
        }
    }

    override suspend fun updateAlarmStatus(id: String, isScheduled: Boolean) {
        taskDao.updateAlarmStatus(id, isScheduled)
    }
}
