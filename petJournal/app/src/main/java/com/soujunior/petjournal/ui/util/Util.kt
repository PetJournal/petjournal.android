package com.soujunior.petjournal.ui.util

/**
 * isValidLenght = irá retornar um True se o campo de String não estiver Blank, o tamanho da String
 * não for menor que 3 ou maior que 30, e se o campo String não estiver vazio*/
fun isValidLenght(input: String): Boolean {
    return if (input.isNotBlank()) {
        input.length < 3 || input.length > 30 && input.isNotEmpty()
    } else false
}

/**
 * Se a String de entrada contiver algum caractere que corresponda à expressão regular, a função
 * retornará true, caso contrário, ela retornará false. Portanto, para usar essa função, basta
 * chamar a função e passar a String que você deseja verificar como argumento. O valor de retorno
 * será true se a String contiver algum caractere especial ou número, ou false se não contiver.*/
fun hasSpecialCharOrNumber(input: String): Boolean {
    val regex = Regex("[^a-zA-Z ]")
    return regex.containsMatchIn(input)
}

fun isEmail(input: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(input)
}

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