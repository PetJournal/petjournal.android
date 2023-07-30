package com.soujunior.domain.usecase.auth.util

data class ValidationResult(
    val success: Boolean,
    val errorMessage: List<String>? = null
)
