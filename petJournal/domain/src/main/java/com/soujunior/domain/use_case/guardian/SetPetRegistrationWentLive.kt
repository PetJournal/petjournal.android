package com.soujunior.domain.use_case.guardian

import com.soujunior.domain.repository.AppInfoDataBase
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class SetPetRegistrationWentLive(private val db: AppInfoDataBase) : BaseUseCase<Boolean, String>() {
    override suspend fun doWork(value: Boolean): DataResult<String> {
        return try {
            db.setIsPetRegistrationWentLive(value)
            DataResult.Success("true")
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}