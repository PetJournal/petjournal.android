package com.soujunior.petjournal.ui.accountManager.changePasswordScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soujunior.domain.entities.auth.PasswordModel
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordViewModel
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.sendNewPassword
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.components.Checkbox
import com.soujunior.petjournal.ui.components.ConfirmPassword
import com.soujunior.petjournal.ui.components.Password
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.states.States

@Composable
fun MyApp( changePasswordViewModel: ChangePasswordViewModel) {
    val checkbox = States.checked.current
    val hasPasswordError = States.localPasswordError.current
    val hasErrorOnConfirmPassword = States.localConfirmPasswordError.current
    val password = States.localPasswordState.current
    val confirmPassword = States.localConfirmPasswordState.current
    val enableButton =
        if (password.value.isNotBlank() && confirmPassword.value.isNotBlank()) {
            (!hasPasswordError.value && !hasErrorOnConfirmPassword.value)
        } else {
            false
        }
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(start = 20.dp, end = 20.dp),
        ) {
            item {
                CreateTitleAndImageLogo(
                    title = "Crie uma nova senha!",
                    styleTitle = MaterialTheme.typography.displayMedium,
                    spaceTop = 60.dp,
                    spaceBetween = 40.dp,
                    spaceBottom = 30.dp
                )
            }
            item { Password(modifier = Modifier.fillMaxWidth()) }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item { ConfirmPassword(modifier = Modifier.fillMaxWidth()) }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item { Checkbox(text = "É necessário que todos os dispositivos acesssem sua conta com a nova senha ?") }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item { Button(
                submit = {
                    sendNewPassword(PasswordModel(password.value), checkbox.value, changePasswordViewModel)
                },
                enableButton = enableButton,
                text = "Redefinir Senha"
            ) }
        }
    }
}