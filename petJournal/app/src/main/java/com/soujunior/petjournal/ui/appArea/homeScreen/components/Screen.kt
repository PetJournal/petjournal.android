package com.soujunior.petjournal.ui.appArea.homeScreen.components

import android.annotation.SuppressLint
import android.content.ClipData.Item
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.data.repository.UserData
import com.soujunior.data.repository.UserRepository
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.util.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalPagerApi
@Composable
fun Screen(navController: NavController) {
    //TODO: (Gelson) 1: Isso aqui não deve ocorrer como já mencionado, o controle dos modos dark e light devem ser feitos nos componentes que estão sendo reutilizados.
    val darkTheme = isSystemInDarkTheme()
    Scaffold(
        bottomBar = { NavigationBar(navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                ) {

                    item { Header(navController) }

                    item { Body(navController) }
                    //TODO: (Gelson) 13: não entendi bem o Spacer aqui
                    item { Spacer(modifier = Modifier.height(5.dp)) }

                }
            }
        }
    )

}