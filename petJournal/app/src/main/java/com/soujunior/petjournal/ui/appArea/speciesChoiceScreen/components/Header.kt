package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soujunior.petjournal.ui.util.UserViewModel


@Composable
fun Header() {
    val userViewModel: UserViewModel = viewModel()
    val userName = userViewModel.userName.value
    LaunchedEffect(Unit) {
        userViewModel.loadUserData()
    }

    Spacer(modifier = Modifier.padding(12.dp))

    Text(
        text = "Olá Gelson, gostaríamos de saber qual a espécie do seu Pet:",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.padding(bottom = 20.dp))

}

@Preview
@Composable
fun PreviewHeader() {
    Header()
}