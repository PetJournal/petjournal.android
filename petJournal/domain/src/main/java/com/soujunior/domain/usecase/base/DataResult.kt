package com.soujunior.domain.usecase.base

sealed class DataResult<out T> {
    class Success<out T>(val data: T) : DataResult<T>()
    object Failure : DataResult<Nothing>()

    val isSuccess get() = this is Success<T>
    val isFailure get() = this is Failure
    val success get() = (this as Success<T>)

    fun handleResult(success: (T) -> Unit, error: () -> Unit) {
        when (this) {
            is Success -> success(data)
            is Failure -> error()
        }
    }
}
