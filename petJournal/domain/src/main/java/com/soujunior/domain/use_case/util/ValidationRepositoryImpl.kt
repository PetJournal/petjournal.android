package com.soujunior.domain.use_case.util

import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.repository.ValidationRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

class ValidationRepositoryImpl : ValidationRepository {
    override fun validateEmail(email: String): ValidationResult {
        return if (email.isBlank() || email.isEmpty()) {
            ValidationResult(
                success = false,
                errorMessage = listOf("*Campo Obrigatório")
            )
        } else {
            val correctFormat = isValidString(email)

            ValidationResult(
                success = correctFormat,
                errorMessage = if (correctFormat) null else listOf("Formato de email incorreto")
            )
        }
    }

    override fun inputSpecieType(value: String): ValidationResult {

        val regex = "[A-Za-zÀ-ÖØ-öø-ÿ ]+".toRegex()
        return if (value.isEmpty()) {
            ValidationResult(
                success = false,
                errorMessage = listOf("* Campo obrigatório!")
            )
        } else if (value.length < 2 || value.length > 30 || !value.matches(regex)) {
            ValidationResult(
                success = false,
                errorMessage = listOf("* O nome fornecido deve ter entre 2 e 30 caracteres, não são permitidos caracteres especiais, nem números. Por favor, insira um nome válido.")
            )
        } else
            ValidationResult(success = true)
    }

    override fun inputPetName(name: String): ValidationResult {

        return if (name.isEmpty()) {
            ValidationResult(
                success = false,
                errorMessage = listOf("* Campo Obrigatório!")
            )
        } else if (hasSpecialChar(name)) {
            ValidationResult(
                success = false,
                errorMessage = listOf(
                    "* Não são permitidos caracteres especiais. " +
                            "Por favor, insira um nome válido."
                )
            )
        } else if (isValidLenght(name)) {
            ValidationResult(
                success = false,
                errorMessage = listOf("* O nome fornecido deve ter entre 2 e 30 caracteres.")
            )
        } else
            ValidationResult(success = true)

    }

    override fun inputPetGender(value: String): ValidationResult {
        return if (value == "M" || value == "F")
            ValidationResult(success = true)
        else
            ValidationResult(
                success = false,
                errorMessage = listOf("* Campo Obrigatório!")
            )
    }

    override fun validateField(value: String): ValidationResult {
        return if (value.isEmpty())
            ValidationResult(
                success = false,
                errorMessage = listOf("*Campo Obrigatório")
            )
        else
            ValidationResult(success = true)
    }

    override fun validatePassword(password: String): ValidationResult {
        val listItens = countCharacters(password)
        val listErrorMessage: MutableList<String>? = mutableListOf()

        var count = 0

        if (password.isNotBlank()) {
            if (listItens[0] < 2)
                listErrorMessage?.add("Pelo menos duas letras Maiusculas (ex: F, G, ...)") else count++

            if (listItens[1] < 2)
                listErrorMessage?.add("Pelo menos duas letras Minusculas (ex: f, g, ...)") else count++

            if (listItens[2] < 2)
                listErrorMessage?.add("Pelo menos dois Simbolos (ex: %, &, @...)") else count++

            if (listItens[3] < 2)
                listErrorMessage?.add("Pelo menos dois Numeros (ex: 2, 5, ...)") else count++
        } else {
            count++
            listErrorMessage?.add("O campo não pode ficar em branco!")
        }

        val hasError = count != 4

        return if (hasError) {
            ValidationResult(success = false, errorMessage = listErrorMessage)
        } else {
            ValidationResult(success = true)
        }
    }

    override fun validateName(name: String): ValidationResult {
        val listErrorMessage: MutableList<String> = mutableListOf()
        var count = 0

        if (name.isNotBlank()) {
            if (isValidLenght(name))
                listErrorMessage.add("O campo precisa ter entre 3 e 30 caracteres..") else count++

            if (hasSpecialCharOrNumber(name))
                listErrorMessage.add("Caracteres especiais não são permitidos!") else count++
        } else {
            count++
            listErrorMessage.add("O campo não pode ficar em branco!")
        }

        val hasError = count != 2
        return if (hasError) {
            ValidationResult(success = false, errorMessage = listErrorMessage)
        } else {
            ValidationResult(success = true)
        }
    }

    override fun validateLastName(lastName: String): ValidationResult {
        return validateName(lastName)
    }

    override fun validatePhone(phone: String): ValidationResult {
        val listErrorMessage: MutableList<String> = mutableListOf()
        var count = 0

        if (phone.length in 1..10)
            listErrorMessage.add("Complete o número de telefone!")
        else count++

        val hasError = count != 1

        return if (hasError) {
            ValidationResult(success = false, errorMessage = listErrorMessage)
        } else {
            ValidationResult(success = true)
        }
    }

    override fun validateRepeatedPassword(
        repeatedPassword: String,
        password: String
    ): ValidationResult {
        val listErrorMessage: MutableList<String> = mutableListOf()
        var count = 0

        if (repeatedPassword.isBlank())
            listErrorMessage.add("O campo não pode estar em branco!") else count++
        if (repeatedPassword != password)
            listErrorMessage.add("As senhas devem ser idênticas!") else count++

        val hasError = count != 2

        return if (hasError) {
            ValidationResult(success = false, errorMessage = listErrorMessage)
        } else {
            ValidationResult(success = true, errorMessage = null)
        }
    }

    override fun validatePrivacyPolicy(value: Boolean): ValidationResult {
        return if (!value) {
            ValidationResult(success = false, errorMessage = null)
        } else {
            ValidationResult(success = true, errorMessage = null)
        }
    }

    override fun validateCodeOTP(codeOTP: String): ValidationResult {
        val listErrorMessage: MutableList<String> = mutableListOf()
        var count = 0

        if (codeOTP.isNotBlank()) {
            if (!isStringOnlyDigits(codeOTP))
                listErrorMessage.add("Caracteres especiais e letras não são permitidos!") else count++
        } else {
            count++
            listErrorMessage.add("O campo não pode ficar em branco!")
        }

        val hasError = count != 1
        return if (hasError) {
            ValidationResult(success = false, errorMessage = listErrorMessage)
        } else {
            ValidationResult(success = true)
        }

    }

    override fun validateDate(date: String): ValidationResult {
        val listErrorMessage: MutableList<String> = mutableListOf()
        var count = 0

        if (!date.matches(Regex("\\d{8}"))) {
            listErrorMessage.add("* Campo Obrigatório!")
        } else {
            val day = date.substring(0, 2).toInt()
            val month = date.substring(2, 4).toInt()
            val year = date.substring(4, 8).toInt()

            try {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
                dateFormat.isLenient = false
                dateFormat.parse("$day/$month/$year")

                val currentDate = Calendar.getInstance()

                val providedDate = Calendar.getInstance()
                providedDate.set(year, month - 1, day)

                val minAllowedDate = Calendar.getInstance()
                minAllowedDate.set(1993, Calendar.JANUARY, 1)

                if (providedDate.after(currentDate)) {
                    listErrorMessage.add("Ops! Verifique se a data preenchida está correta.")
                } else if (providedDate.before(minAllowedDate)) {
                    listErrorMessage.add("A data não pode ser anterior 1993.")
                } else {
                    count++
                }
            } catch (e: Exception) {
                listErrorMessage.add("Ops! Verifique se a data preenchida está correta.")
            }
        }

        val hasError = count != 1

        return if (hasError) {
            ValidationResult(success = false, errorMessage = listErrorMessage)
        } else {
            ValidationResult(success = true)
        }
    }

    override fun validateDropDownRaceOthers(raceOther: String): ValidationResult {
        return if (raceOther.lowercase() == "outro") {
            ValidationResult(success = true)
        } else {
            ValidationResult(success = false)
        }
    }

    override fun validateDropdown(value: String, list: List<PetSizeItemModel>): ValidationResult {

        return if (value.isNotEmpty() && list.any { it.name == value }) {
            ValidationResult(
                success = true,
            )
        } else {
            ValidationResult(
                success = false,
                errorMessage = listOf("* Campo obrigatório!")
            )

        }
    }

    override fun validateDropDownPetRace(
        value: String,
        list: List<PetRaceItemModel>
    ): ValidationResult {
        return if (value.isNotEmpty() && list.any { it.name == value }) {
            ValidationResult(
                success = true,
            )
        } else {
            ValidationResult(
                success = false,
                errorMessage = listOf("* Raça $value não encontrada. Escolha uma raça da lista a baixo ou escolha 'outro' para registrar uma nova.")
            )

        }
    }

    private fun isCodeValidLenght(input: String): Boolean {
        return if (input.isNotBlank()) {
            input.length == 6 && input.isNotEmpty()
        } else false
    }

    /**
     * isValidLength = will return True if the String field is not Blank, the length of the String
     * is not less than 3 or greater than 30, and if the String field is not empty */
    private fun isValidLenght(input: String): Boolean {
        return if (input.isNotBlank()) {
            input.length < 3 || input.length > 30 && input.isNotEmpty()
        } else false
    }

    /**
     * If the input String contains any characters that match the regular expression, the function
     * will return true, otherwise it will return false. So, to use this function, just
     * call the function and pass the String you want to check as an argument. the return value
     * will be true if the String contains any special characters or numbers, or false if it does not.*/
    private fun hasSpecialCharOrNumber(input: String): Boolean {
        val regex = Regex("[^a-zA-ZÀ-ÖØ-öø-ÿ ]")
        return regex.containsMatchIn(input)
    }

    /**
     * Same as the function above, with the exception that this function accepts number in the regex.
     * In other words, the function will only return false if the input contains a special char.
     *
     * @return true if contains char, otherwise, returns false
     * */
    private fun hasSpecialChar(input: String): Boolean {
        val regex = Regex("[^a-zA-ZÀ-ÖØ-öø-ÿ0-9]")
        return regex.containsMatchIn(input)
    }

    private fun isStringOnlyDigits(input: String): Boolean {
        return input.all { it.isDigit() }
    }

    /**
     * To use the isEmail function, simply call the function and pass the String you want to check as
     * argument. The return value will be true if the String matches a valid email address, or
     * false if it doesn't match. Here is the implementation of the isEmail function:*/
    private fun isEmail(input: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(input)
    }

    /**
     * To use the countCharacters function, just call the function and pass the String you want
     * check as argument. The return value will be a list of four integers representing
     * the count of characters in the String. Here is the implementation of the countCharacters function:
     * */
    private fun countCharacters(str: String): List<Int> {
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

    private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" +
                "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    private fun isValidString(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    override fun validatePetCastration(value: Boolean?): ValidationResult {
        return if (value == true || value == false)
            ValidationResult(success = true)
        else
            ValidationResult(
                success = false,
                errorMessage = listOf("* Campo Obrigatório!")
            )
    }
}
