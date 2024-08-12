package com.soujunior.petjournal.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DeleteDialog(
    onDismissRequest: ()-> Unit,
    onConfirmation: ()-> Unit,
    dialogTitle: String,
    dialogText: String,
){
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = {
                onConfirmation()
                onDismissRequest()
            }) {
                Text(text = "Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Cancelar")
            }
        },
        title = { Text(dialogTitle) },
        text = { Text(dialogText) }

    )
}

@Preview
@Composable
fun PreviewDialog(){
    DeleteDialog({}, {}, "Deletar Card", "Deseja mesmo deletar as informações deste pet?")
}

