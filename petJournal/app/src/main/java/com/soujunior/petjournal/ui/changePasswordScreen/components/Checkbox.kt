package com.soujunior.petjournal.ui.changePasswordScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.soujunior.petjournal.ui.registerScreen.state.StatesRegister

@Composable
fun Checkbox(modifier: Modifier = Modifier) {
    var checked = StatesRegister.localCheckedState.current
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Column {
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = { checked.value = it },
                    modifier = modifier.clickable { }
                )
            }
            Column {
                Text(
                    text = "É necessário que todos os dispositivos acesssem sua conta com a nova senha ?",
                    modifier = modifier.clickable(onClick = { checked.value = !checked.value }),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}