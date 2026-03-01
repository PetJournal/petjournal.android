package com.soujunior.petjournal.ui.util

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import okhttp3.Interceptor

/**
 * isValidLength = will return True if the String field is not Blank, the length of the String
 * is not less than 3 or greater than 30, and if the String field is not empty */
fun isValidLenght(input: String): Boolean {
    return if (input.isNotBlank()) {
        input.length < 3 || input.length > 30 && input.isNotEmpty()
    } else {
        false
    }
}

/**
 * If the input String contains any characters that match the regular expression, the function
 * will return true, otherwise it will return false. So, to use this function, just
 * call the function and pass the String you want to check as an argument. the return value
 * will be true if the String contains any special characters or numbers, or false if it does not.*/
fun hasSpecialCharOrNumber(input: String): Boolean {
    val regex = Regex("[^a-zA-ZÀ-ÖØ-öø-ÿ ]")
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

val timeoutObserverInterceptor =
    Interceptor { chain ->
        val request = chain.request()
        val startTime = System.currentTimeMillis()

        Log.d("NetworkObserver", "➡️ Enviando requisição para: ${request.url()}")

        val response = chain.proceed(request)

        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime

        Log.d("NetworkObserver", "✅ Resposta de: ${request.url()} recebida em ${duration}ms")

        response
    }

fun Modifier.shimmerEffect(): Modifier =
    composed {
        val transition = rememberInfiniteTransition(label = "shimmer")
        val translateAnim =
            transition.animateFloat(
                initialValue = 0f,
                targetValue = 1000f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(
                                durationMillis = 1000,
                                easing = FastOutSlowInEasing,
                            ),
                        repeatMode = RepeatMode.Restart,
                    ),
                label = "shimmer_float",
            )

        val shimmerColors =
            listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            )

        val brush =
            Brush.linearGradient(
                colors = shimmerColors,
                start = Offset.Zero,
                end = Offset(x = translateAnim.value, y = translateAnim.value),
            )

        this.background(brush)
    }
