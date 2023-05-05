package com.soujunior.petjournal.ui.loginScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.soujunior.petjournal.ui.forgotPasswordScreen.ForgotPasswordScreen
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.light_primary

@Composable
fun RememberForgotPassword(navController: NavController) {

    var checked by States.checked.current
    Row(
        modifier = Modifier.fillMaxWidth(0.95f),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked_ -> checked = checked_ },
                    colors = CheckboxDefaults.colors(Color(light_primary.value))
                )
                Text(
                    text = "Lembrar",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
        Text(
            text = "Esqueci minha senha",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .clickable(onClick = { navController.navigate("forgotPasswordScreen")}),
        )
    }
}