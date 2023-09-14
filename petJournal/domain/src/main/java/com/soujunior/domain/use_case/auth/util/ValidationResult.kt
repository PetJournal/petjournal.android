package com.soujunior.domain.use_case.auth.util

data class ValidationResult(
    val success: Boolean,
    val errorMessage: List<String>? = null
)
