package com.soujunior.domain.use_case.guardian

import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class SaveGuardianContactUseCase(private val repository: Repository) :
    BaseUseCase<String, Unit>() {

    override suspend fun doWork(value: String): DataResult<Unit> {
        return try {
            repository.saveGuardianContact(email = value, phone = "")
            DataResult.Success(Unit)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
