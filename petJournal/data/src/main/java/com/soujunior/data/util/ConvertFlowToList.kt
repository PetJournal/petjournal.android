package com.soujunior.data.util

import com.soujunior.domain.model.PetInformationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

fun convertFlowToList(flow: Flow<List<PetInformationModel>>): List<PetInformationModel> {
    val resultList = mutableListOf<PetInformationModel>()

    runBlocking {
        flow.collect { list ->
            resultList.addAll(list)
        }
    }

    return resultList
}
