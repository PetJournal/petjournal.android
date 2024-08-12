package com.soujunior.domain.use_case.base

sealed class DataResult<out T> {
    class Success<out T>(val data: T) : DataResult<T>()
    class Failure(val throwable: Throwable?) : DataResult<Nothing>()

    val isSuccess get() = this is Success<T>
    val isFailure get() = this is Failure
    val success get() = (this as Success<T>)

    fun handleResult(success: (T) -> Unit, error: (Throwable?) -> Unit) {
        when (this) {
            is Success -> success(data)
            is Failure -> error(throwable)
        }
    }
}

suspend fun <T> DataResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): DataResult<T> = apply {
    if (this is DataResult.Success<T>) {
        executable(data)
    }
}

suspend fun <T> DataResult<T>.onFailure(
    executable: suspend (Throwable?) -> Unit
): DataResult<T> = apply {
    if (this is DataResult.Failure) {
        executable(throwable)
    }
}

