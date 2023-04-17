package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister

@Composable
fun PrivacyPolicyCheckbox() {
    var showPrivacyPolicy by StatesRegister.showPrivacyPolicy.current
    var checked by StatesRegister.localCheckedState.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 0.dp, end = 0.dp, top = 16.dp)
            .fillMaxWidth()
    ) {
        Column {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                modifier = Modifier.clickable { }
            )
        }
        Column {
            Text(
                text = "Eu concordo com a política de privacidade",
                modifier = Modifier.clickable(onClick = { showPrivacyPolicy = true }),
                style = MaterialTheme.typography.body1
            )
        }
    }
    if (showPrivacyPolicy) {
        PrivacyPolicy()
    }
}