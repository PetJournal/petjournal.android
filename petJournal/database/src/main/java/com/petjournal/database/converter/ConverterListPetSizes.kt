package com.petjournal.database.converter

import androidx.room.TypeConverter
import com.soujunior.domain.model.request.PetSizeItemModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object ConverterListPetSizes {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val type = Types.newParameterizedType(List::class.java, PetSizeItemModel::class.java)
    private val adapter = moshi.adapter<List<PetSizeItemModel>>(type)

    @TypeConverter
    fun fromPetSizeItemModelList(value: List<PetSizeItemModel>?): String? {
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toPetSizeItemModelList(value: String?): List<PetSizeItemModel>? {
        return value?.let { adapter.fromJson(it) }
    }
}