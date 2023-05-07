package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister

@Composable
fun PrivacyPolicyCheckbox(modifier: Modifier = Modifier.fillMaxWidth()) {
    var showPrivacyPolicy by StatesRegister.showPrivacyPolicy.current
    var checked by StatesRegister.localCheckedState.current
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
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
    }
    if (showPrivacyPolicy) {
        PrivacyPolicy()
    }
}