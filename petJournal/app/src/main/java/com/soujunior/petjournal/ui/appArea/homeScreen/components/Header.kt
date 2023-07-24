package com.soujunior.petjournal.ui.appArea.homeScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.util.UserViewModel

@Composable
fun Header(navController: NavController) {
    val userViewModel: UserViewModel = viewModel()
    val userName = userViewModel.userName.value
    //TODO: (Gelson) 2:
    val darkTheme = isSystemInDarkTheme()
    LaunchedEffect(Unit) {
        userViewModel.loadUserData()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(start = 20.dp, top = 40.dp)
    ) {
        /*TODO: (Gelson) 3: Evitar de definir o modo de cor (dark & light) dentro do codigo. Isso
           dificultará uma manutenção futura.
           - Caso precise de algum componente que realize uma
           determinada ação, tente criar um componente novo e em caso de dúvida, consulte os
           componentes já criados (exceto o componente Button), e se precisar peça ajuda.*/
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
            color = if (darkTheme) MaterialTheme.colorScheme.primary else Color.Unspecified,
            modifier = Modifier
                .padding(end = 30.dp)
                .weight(0.5f),
        )
        //TODO: (Gelson) 4: adicionar toast para quando for clicado
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 13.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "Menu",
                tint = if (darkTheme) MaterialTheme.colorScheme.primary else Color.Unspecified,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}