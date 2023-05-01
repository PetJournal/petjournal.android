package com.soujunior.petjournal.ui.forgotPasswordScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.forgotPasswordScreen.components.ComponentButton
import com.soujunior.petjournal.ui.forgotPasswordScreen.components.CreateLogoView
import com.soujunior.petjournal.ui.forgotPasswordScreen.components.EditTextComponent
import com.soujunior.petjournal.ui.forgotPasswordScreen.components.MyApp
import com.soujunior.petjournal.ui.forgotPasswordScreen.components.TextViewCompBody1
import com.soujunior.petjournal.ui.forgotPasswordScreen.components.TextViewCompH2
import com.soujunior.petjournal.ui.registerScreen.components.ImageLogo
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.Shapes
import com.soujunior.petjournal.ui.util.*


@Composable
fun ForgotPasswordScreen(navController: NavController) {
    MyApp(navController)
}






