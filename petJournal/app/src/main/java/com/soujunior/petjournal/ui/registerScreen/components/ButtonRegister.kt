package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun ButtonRegister(submit: () -> Unit, enableButton: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { submit() },
            enabled = enableButton,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
                .size(height = 50.dp, width = 200.dp),
            shape = Shapes.large
        ) {
            Text(
                text = "Cadastrar",
                style = MaterialTheme.typography.button
            )
        }
    }
}