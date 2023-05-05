package com.soujunior.petjournal.ui.forgotPasswordScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.soujunior.petjournal.ui.states.States.localEmailError
import com.soujunior.petjournal.ui.states.States.localEmailState
import com.soujunior.petjournal.ui.theme.DarkColors
import com.soujunior.petjournal.ui.theme.LightColors


@Composable
fun Form(modifier: Modifier, roundedCornerShape: RoundedCornerShape) {

    TextViewCompBody1(text = "Qual o seu email de cadastro?", pStart = 20, pTop = 70, alignment = Alignment.Bottom, arrangement = Arrangement.Start, withTheme = true)
    EditTextComponent(
        modifier = modifier,
        roundedCornerShape = roundedCornerShape,
        stateP = localEmailState.current,
        stateE = localEmailError.current,
        label = "Email",
        placeHolder = "eg: exemple@petjournal.com"
    )

}