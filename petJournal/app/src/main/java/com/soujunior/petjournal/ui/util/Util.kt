package com.soujunior.petjournal.ui.util

/**
 * isValidLength = will return True if the String field is not Blank, the length of the String
 * is not less than 3 or greater than 30, and if the String field is not empty */
fun isValidLenght(input: String): Boolean {
    return if (input.isNotBlank()) {
        input.length < 3 || input.length > 30 && input.isNotEmpty()
    } else false
}

/**
 * If the input String contains any characters that match the regular expression, the function
 * will return true, otherwise it will return false. So, to use this function, just
 * call the function and pass the String you want to check as an argument. the return value
 * will be true if the String contains any special characters or numbers, or false if it does not.*/
fun hasSpecialCharOrNumber(input: String): Boolean {
    val regex = Regex("[^a-zA-Z ]")
    return regex.containsMatchIn(input)
}

/**
 * To use the isEmail function, simply call the function and pass the String you want to check as
 * argument. The return value will be true if the String matches a valid email address, or
 * false if it doesn't match. Here is the implementation of the isEmail function:*/
fun isEmail(input: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(input)
}

/**
 * To use the countCharacters function, just call the function and pass the String you want
 * check as argument. The return value will be a list of four integers representing
 * the count of characters in the String. Here is the implementation of the countCharacters function:
 * */
fun countCharacters(str: String): List<Int> {
    var digitosMaiusculos = 0
    var digitosMinusculos = 0
    var simbolos = 0
    var numeros = 0
    for (c in str) {
        when {
            c.isUpperCase() -> digitosMaiusculos++
            c.isLowerCase() -> digitosMinusculos++
            c.isDigit() -> numeros++
            else -> simbolos++
        }
    }
    return listOf(digitosMaiusculos, digitosMinusculos, simbolos, numeros)
}