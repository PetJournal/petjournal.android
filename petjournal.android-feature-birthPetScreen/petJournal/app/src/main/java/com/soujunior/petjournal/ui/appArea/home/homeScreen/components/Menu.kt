package com.soujunior.petjournal.ui.appArea.home.homeScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.MenuRow

@Composable
fun Menu(navController: NavController) {
    Column(Modifier.fillMaxWidth()) {
        MenuRow(
            items = listOf(
                Pair(R.drawable.agenda, MaterialTheme.colorScheme.secondaryContainer),
                Pair(R.drawable.servicos, MaterialTheme.colorScheme.onSecondaryContainer)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        MenuRow(
            items = listOf(
                Pair(R.drawable.vacinas, MaterialTheme.colorScheme.tertiary),
                Pair(R.drawable.vermifugos, MaterialTheme.colorScheme.surfaceTint)
            )
        )
    }
}

