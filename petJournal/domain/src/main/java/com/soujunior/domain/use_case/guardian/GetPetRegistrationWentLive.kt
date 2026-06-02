package com.soujunior.domain.use_case.guardian

import android.content.ContentValues.TAG
import android.util.Log
import com.soujunior.domain.repository.appinfo.AppInfoDatabase
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetPetRegistrationWentLive(private val db: AppInfoDatabase) : BaseUseCase<Unit, Boolean>() {
    override suspend fun doWork(value: Unit): DataResult<Boolean> {
        return try {
            val result = db.getIsPetRegistrationWentLive()
            DataResult.Success(data = result)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
