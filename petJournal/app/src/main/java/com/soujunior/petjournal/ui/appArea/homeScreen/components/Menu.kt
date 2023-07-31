package com.soujunior.petjournal.ui.appArea.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CardButton

@Composable
fun Menu(navController: NavController) {
    val imageSize = 140.dp
    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CardButton(
                image = painterResource(id = R.drawable.agenda),
                cardColor = MaterialTheme.colorScheme.secondaryContainer,
                submit = { },
                modifier = Modifier.size(imageSize)
            )
            CardButton(
                image = painterResource(id = R.drawable.servicos),
                cardColor = MaterialTheme.colorScheme.onSecondaryContainer,
                submit = { },
                modifier = Modifier.size(imageSize)
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CardButton(
                image = painterResource(id = R.drawable.vacinas),
                cardColor = MaterialTheme.colorScheme.tertiary,
                submit = { },
                modifier = Modifier.size(imageSize)
            )
            CardButton(
                image = painterResource(id = R.drawable.vermifugos),
                cardColor = MaterialTheme.colorScheme.surfaceTint,
                submit = { },
                modifier = Modifier.size(imageSize)
            )
        }

    }
}