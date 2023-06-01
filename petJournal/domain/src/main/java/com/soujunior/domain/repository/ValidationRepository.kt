package com.soujunior.domain.repository

import com.soujunior.domain.usecase.auth.util.ValidationResult

interface ValidationRepository {
    fun validateName(name: String) : ValidationResult
    fun validateLastName(lastName: String) : ValidationResult
    fun validateEmail(email: String) : ValidationResult
    fun validatePhone(phone: String) : ValidationResult
    fun validatePassword(password: String, newPassword : String) : ValidationResult
    fun validateNewPassword(newPassword: String, password: String) : ValidationResult
    fun validatePrivacyPolicy(value: Boolean) : ValidationResult
}