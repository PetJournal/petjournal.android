/*
package com.soujunior.petjournal.ui.appArea.menu

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun menuDropDown(){
    val showDropdownMenu = remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = showDropdownMenu.value,
        onDismissRequest = { showDropdownMenu.value = false },
        modifier = Modifier.padding(end = 20.dp)
    ) {
        DropdownMenuItem(
            onClick = {
                showDropdownMenu.value = false
                Log.e(ContentValues.TAG, "Logout")
            },
            text = {
                Text(text = "Logout arrumar", fontSize = 18.sp)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout"
                )
            }
        )
    }
}*/
