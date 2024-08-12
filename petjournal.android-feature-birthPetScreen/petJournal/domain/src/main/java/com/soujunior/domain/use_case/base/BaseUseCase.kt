package com.soujunior.domain.use_case.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Params, out R> {
    private val superVisorJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + superVisorJob)

    protected abstract suspend fun doWork(value: Params): DataResult<R>

    suspend fun execute(value: Params): DataResult<R> {
        return withContext(scope.coroutineContext) {
            try {
                withContext(Dispatchers.IO) { doWork(value) }
            } catch (e: Throwable) {
                DataResult.Failure(e)
            }
        }
    }

    fun cancelWork() = scope.coroutineContext.cancelChildren()
}