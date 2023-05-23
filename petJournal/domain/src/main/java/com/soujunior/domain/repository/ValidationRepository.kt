package com.soujunior.domain.repository

import com.soujunior.domain.usecase.auth.util.ValidationResult

interface ValidationRepository {
    fun validateEmail(email: String) : ValidationResult
    fun validatePassword(password: String) : ValidationResult
}