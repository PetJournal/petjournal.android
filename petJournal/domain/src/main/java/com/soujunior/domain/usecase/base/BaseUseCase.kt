package com.soujunior.domain.usecase.base

import android.util.Log
import kotlinx.coroutines.*

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