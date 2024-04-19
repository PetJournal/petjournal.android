package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.state.StatesRegister

@Composable
fun PrivacyPolicyCheckbox(
    modifier: Modifier = Modifier.fillMaxWidth(),
    valueChecked: Boolean,
    onEvent: (Boolean) -> Unit
) {
    var showPrivacyPolicy by StatesRegister.showPrivacyPolicy.current
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                androidx.compose.material3.Checkbox(
                    checked = valueChecked,
                    onCheckedChange = { onEvent(it) },
                    modifier = Modifier.clickable { }
                )
            }
            Column {
                Text(
                    text = "Eu concordo com a política de privacidade",
                    modifier = Modifier.clickable(onClick = { showPrivacyPolicy = true }),
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
                )
            }
        }
    }
    if (showPrivacyPolicy) {
        PrivacyPolicy()
    }
}