package com.soujunior.domain.usecase.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Params, out R> {
    private val superVisorJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + superVisorJob)

    protected abstract suspend fun doWork(value: Params?): R

    suspend fun execute(value: Params?): DataResult<R> {
        return withContext(scope.coroutineContext) {
            try {
                val result = withContext(Dispatchers.IO) { doWork(value) }
                DataResult.Success(result)
            } catch (e: Throwable) {
                DataResult.Failure(e)
            }
        }
    }

    fun cancelWork() = scope.coroutineContext.cancelChildren()
}