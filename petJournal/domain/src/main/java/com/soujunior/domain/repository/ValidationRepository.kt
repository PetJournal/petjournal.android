package com.soujunior.domain.repository

import com.soujunior.domain.use_case.util.ValidationResult

interface ValidationRepository {
    fun validateName(name: String) : ValidationResult
    fun validateLastName(lastName: String) : ValidationResult
    fun validateEmail(email: String) : ValidationResult
    fun validatePhone(phone: String) : ValidationResult
    fun validatePassword(password: String) : ValidationResult
    fun validateField(value: String) : ValidationResult
    fun inputSpecieType(value: String) : ValidationResult
    fun inputPetName(value: String) : ValidationResult
    fun inputPetGender(value: String): ValidationResult
    fun validateRepeatedPassword(repeatedPassword: String, password: String) : ValidationResult
    fun validatePrivacyPolicy(value: Boolean) : ValidationResult
    fun validateCodeOTP(codeOTP: String) : ValidationResult

    fun validateDropDownRaceOthers(raceOtrher : String) : ValidationResult
    fun validateDropdownSize(value : String) : ValidationResult
}