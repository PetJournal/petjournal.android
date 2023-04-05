package com.gusoliveira.domain.usecase.base

import android.util.Log
import com.soujunior.domain.usecase.base.DataResult
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
                Log.e("LogSucesso", "BaseUseCase - FALHA- $e")
                DataResult.Failure
            }
        }
    }
    fun cancelWork() = scope.coroutineContext.cancelChildren()
}