package com.soujunior.petjournal.ui.appArea.homeScreen.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.util.UserViewModel

@Composable
fun Header(navController: NavController) {
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel()
    val userName = userViewModel.userName.value
    LaunchedEffect(Unit) {
        userViewModel.loadUserData()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(start = 20.dp, top = 40.dp)
    ) {
        Text(
            buildAnnotatedString {
                append("Olá, ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W900,
                        fontSize = 18.sp
                    )
                ) {
                    append("$userName!")
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(end = 30.dp)
                .weight(0.5f),
        )

        IconButton(
            onClick = {
                Toast.makeText(context, "Clicado", Toast.LENGTH_LONG).show()
            }, modifier = Modifier.padding(end = 13.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}


