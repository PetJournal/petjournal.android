package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.Checkbox

@Composable
fun RememberForgotPassword(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(0.95f),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(text = "Lembrar")
            }
        }
        Text(
            text = "Esqueci minha senha",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .clickable(onClick = { navController.navigate("forgotPassword") }),
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
    }
}