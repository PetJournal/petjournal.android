package com.soujunior.petjournal.ui.util

fun isValidEmail(email: String): Boolean {
    // Expressão regular para validar um endereço de email
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
}