package com.petjournal.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.SpecieDTO
import com.soujunior.domain.model.taskModel.SchedulerDTO

class RoomConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromSchedulerDTO(value: SchedulerDTO?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSchedulerDTO(value: String?): SchedulerDTO? {
        if (value == null) return null
        val type = object : TypeToken<SchedulerDTO>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSpecieDTO(value: SpecieDTO?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSpecieDTO(value: String?): SpecieDTO? {
        if (value == null) return null
        val type = object : TypeToken<SpecieDTO>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromBreedDTO(value: BreedDTO?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toBreedDTO(value: String?): BreedDTO? {
        if (value == null) return null
        val type = object : TypeToken<BreedDTO>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSizeDTO(value: SizeDTO?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSizeDTO(value: String?): SizeDTO? {
        if (value == null) return null
        val type = object : TypeToken<SizeDTO>() {}.type
        return gson.fromJson(value, type)
    }
}
