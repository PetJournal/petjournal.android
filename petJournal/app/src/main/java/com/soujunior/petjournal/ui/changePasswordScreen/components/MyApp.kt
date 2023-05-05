package com.soujunior.petjournal.ui.changePasswordScreen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.changePasswordScreen.ChangePasswordViewModel
import com.soujunior.petjournal.ui.changePasswordScreen.sendNewPassword
import com.soujunior.petjournal.ui.registerScreen.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.states.States

@Composable
fun MyApp( changePasswordViewModel: ChangePasswordViewModel ) {
    val checkbox = States.checked.current
    val hasErrorpassword = States.localPasswordError.current
    val hasErrorOnConfirmPassword = States.localConfirmPasswordError.current
    val password = States.localPasswordState.current
    val enableButton = if(hasErrorpassword.value == true || hasErrorOnConfirmPassword.value == true) false else true

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(start = 20.dp, end = 20.dp),
        ) {
            item {
                CreateTitleAndImageLogo(
                    title = "Crie uma nova senha!",
                    styleTitle = MaterialTheme.typography.h2,
                    spaceTop = 60.dp,
                    spaceBetween = 40.dp,
                    spaceBottom = 30.dp
                )
            }
            item { Password(modifier = Modifier.fillMaxWidth()) }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item { ConfirmPassword(modifier = Modifier.fillMaxWidth()) }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item { Checkbox() }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item { Button(
                submit = {
                    sendNewPassword(password.value, changePasswordViewModel)
                },
                enableButton = enableButton,
                text = "Redefinir Senha"
            ) }
        }
    }
}