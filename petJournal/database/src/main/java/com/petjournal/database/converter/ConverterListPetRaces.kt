package com.petjournal.database.converter

import androidx.room.TypeConverter
import com.soujunior.domain.model.request.PetRaceItemModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object ConverterListPetRaces {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val type = Types.newParameterizedType(List::class.java, PetRaceItemModel::class.java)
    private val adapter = moshi.adapter<List<PetRaceItemModel>>(type)

    @TypeConverter
    fun fromPetRaceItemModelList(value: List<PetRaceItemModel>?): String? {
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toPetRaceItemModelList(value: String?): List<PetRaceItemModel>? {
        return value?.let { adapter.fromJson(it) }
    }
}