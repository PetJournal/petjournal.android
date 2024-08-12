package com.soujunior.domain.use_case.guardian

import android.content.ContentValues.TAG
import android.util.Log
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AppInfoDataBase
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetPetRegistrationWentLive(private val db: AppInfoDataBase) : BaseUseCase<Unit, Boolean>() {
    override suspend fun doWork(value: Unit): DataResult<Boolean> {
        return try {
            val result = db.getIsPetRegistrationWentLive()
            Log.e(TAG, "Get: $result")
            DataResult.Success(data = result)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}