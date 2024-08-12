package com.soujunior.domain.use_case.util

data class ValidationResult(
    val success: Boolean,
    val errorMessage: List<String>? = null
)
